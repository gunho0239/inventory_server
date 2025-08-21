package com.example.inventory_server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleRequestResponse {
    private boolean success;
    private String message;

    public SingleRequestResponse() {
        success = false;
        message = "";
    }

}