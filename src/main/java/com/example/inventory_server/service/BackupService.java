package com.example.inventory_server.service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BackupService {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${backup.path}")
    private String backupPath;  // Z:/db-backup


    public LocalDateTime getLastBackupTime() throws Exception {
        File backupDir = new File(backupPath);
        if (!backupDir.exists() || !backupDir.isDirectory())
            throw new IOException("백업 경로를 찾을 수 없습니다: " + backupPath);

        File latestBackup = findLatestBackupFile();
        if (latestBackup == null) return null;

        // 파일명에서 날짜·시간 파싱 (backup_20260221_143000.sql)
        String name = latestBackup.getName()
                .replace("backup_", "")
                .replace(".sql", "");   // "20260221_143000"

        return LocalDateTime.parse(name, DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }


    public boolean backupDatabase() throws Exception {
        String[] urlParts = parseUrl();
        String host = urlParts[0], port = urlParts[1], dbName = urlParts[2];
        System.out.println(host + port + dbName);

        // backupPath 디렉터리 없으면 자동 생성
        File backupDir = new File(backupPath);
        if (!backupDir.exists()) {
            boolean created = backupDir.mkdirs();  // 중간 경로까지 모두 생성
            if (!created) {
                throw new IOException("백업 디렉터리 생성 실패: " + backupPath);
            }
        }

        // 파일명에 날짜+시간 포함 (예: backup_20260221_143000.sql)
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File backupFile = new File(backupPath + "/backup_" + timestamp + ".sql");

        ProcessBuilder pb = new ProcessBuilder(
                "mysqldump",
                "-h", host,
                "-P", port,
                "-u", dbUser,
                "-p" + dbPassword,
                "--no-tablespaces",
                "--single-transaction",
                "--databases", dbName
        );

        pb.redirectOutput(backupFile);
        pb.redirectErrorStream(false);
        Process process = pb.start();
        process.getErrorStream().transferTo(System.err);

        boolean success = process.waitFor() == 0;

        // 백업 성공 시에만 읽기 전용 설정
        if (success) {
            backupFile.setReadOnly();
            System.out.println("백업 완료 (읽기 전용): " + backupFile.getName());
        } else {
            backupFile.delete();  // 실패한 덤프 파일 삭제
        }

        return success;
    }


    public boolean restoreDatabase() throws Exception {
        String[] urlParts = parseUrl();
        String host = urlParts[0], port = urlParts[1], dbName = urlParts[2];

        // NAS 경로에서 가장 최근 백업 파일 탐색
        File backupDir = new File(backupPath);
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            throw new IOException("백업 경로를 찾을 수 없습니다: " + backupPath);
        }

        File latestBackup = findLatestBackupFile();
        if (latestBackup == null)
            throw new IOException("백업 파일이 존재하지 않습니다: " + backupPath);

        System.out.println("복원 대상 파일: " + latestBackup.getName());

        ProcessBuilder pb = new ProcessBuilder(
                "mysql",
                "-h", host,
                "-P", port,
                "-u", dbUser,
                "-p" + dbPassword,
                dbName
        );

        pb.redirectInput(latestBackup);   // 최근 백업 파일을 stdin으로 주입
        pb.redirectErrorStream(true);

        Process process = pb.start();
        process.getInputStream().transferTo(System.out);

        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    private File findLatestBackupFile() throws IOException {
        File backupDir = new File(backupPath);
        if (!backupDir.exists() || !backupDir.isDirectory())
            throw new IOException("백업 경로를 찾을 수 없습니다: " + backupPath);

        return Arrays.stream(backupDir.listFiles())
                .filter(f -> f.isFile() && f.getName().matches("backup_\\d{8}_\\d{6}\\.sql"))
                .max(Comparator.comparing(File::getName))
                .orElse(null);
    }


    private String[] parseUrl() {
        String withoutParams = datasourceUrl.split("\\?")[0];
        System.out.println(withoutParams);

        String hostSection = withoutParams.split("//")[1];
        String hostAndPort = hostSection.split("/")[0];

        String host   = hostAndPort.split(":")[0];
        String port   = hostAndPort.split(":")[1];
        String dbName = hostSection.split("/")[1];

        return new String[]{host, port, dbName};
    }


}
