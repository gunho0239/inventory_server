package com.example.inventory_server.dto;


public class BulkDeleteResponse {
    private int successCount;
    private int failedCount;

    public BulkDeleteResponse() {
        successCount = 0;
        failedCount = 0;
    }

    public void increaseSuccussCount() {successCount++;}
    public void increaseFailedCount() {failedCount++;}

}