package com.example.inventory_server.controller;

import com.example.inventory_server.dto.GeneralRequestResponse;
import com.example.inventory_server.dto.SingleRequestResponse;
import com.example.inventory_server.service.BackupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @PostMapping("/dump")
    public SingleRequestResponse createBackup() {
        SingleRequestResponse response = new SingleRequestResponse();
        try {
            boolean success = backupService.backupDatabase();
            response.setSuccess(success);
            if (success) {
                response.setMessage("백업 성공");
            }
            else {
                response.setMessage("백업 실패");
            }

            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("백업 에러: " + e.getMessage());
            return response;
        }
    }

    @PostMapping("/restore")
    public SingleRequestResponse restoreBackup() {
        SingleRequestResponse response = new SingleRequestResponse();
        try {
            boolean success = backupService.restoreDatabase();
            response.setSuccess(success);
            if (success) {
                response.setMessage("복원 성공");
            }
            else {
                response.setMessage("복원 실패");
            }
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("복원 에러: " + e.getMessage());
            return response;
        }
    }

    @GetMapping("/last-backup")
    public GeneralRequestResponse<LocalDateTime> getLastBackupTime() {
        GeneralRequestResponse<LocalDateTime> response = new GeneralRequestResponse<>();

        try {
            LocalDateTime lastBackupTime = backupService.getLastBackupTime();

            if (lastBackupTime == null)
            {
                response.setMessage("백업 파일이 존재하지 않습니다.");
                return response;
            }

            response.setData(lastBackupTime);
            return response;

        } catch (Exception e) {
            response.setMessage("에러: "  + e.getMessage());
            return response;
        }
    }

}