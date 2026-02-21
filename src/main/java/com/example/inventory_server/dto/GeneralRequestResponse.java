package com.example.inventory_server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralRequestResponse<T> {

    private T data;
    private String message;

    public GeneralRequestResponse() {
        data = null;
        message = "";
    }

}
