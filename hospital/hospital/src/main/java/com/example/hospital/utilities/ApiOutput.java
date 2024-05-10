package com.example.hospital.utilities;

import lombok.Data;

@Data
public class ApiOutput<T> {

    private int status;
    private String message;
    private T DataEntity;

    public ApiOutput(int status, String message, T DataEntity) {
        this.status = status;
        this.message = message;
        this.DataEntity = DataEntity;
    }


}
