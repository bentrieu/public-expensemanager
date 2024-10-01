package com.expensemanager.expensemanager.responseObject;

import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.Date;

@Data
public class BaseResponseObject {

    private Integer statusCode;
    private Date timestamp;

    public BaseResponseObject(Integer statusCode) {
        this.statusCode = statusCode;
        this.timestamp = new Date();
    }

    public BaseResponseObject() {
    }
}
