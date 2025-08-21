package com.example.inventory_server.dto;


public class BulkRequestResponse {
    private int successCount;
    private int failedCount;

    public BulkRequestResponse() {
        successCount = 0;
        failedCount = 0;
    }

    public void increaseSuccussCount() {successCount++;}
    public void increaseFailedCount() {failedCount++;}

}