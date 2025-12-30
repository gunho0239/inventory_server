package com.example.inventory_server.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BulkRequestResponseWithId {
    private List<Long> successIds;
    private List<Long> failedIds;

    public BulkRequestResponseWithId() {
        successIds = new ArrayList<>();
        failedIds = new ArrayList<>();
    }

    public void addSuccessId(Long id) {successIds.add(id);}
    public void addFailedId(Long id) {failedIds.add(id);}
}
