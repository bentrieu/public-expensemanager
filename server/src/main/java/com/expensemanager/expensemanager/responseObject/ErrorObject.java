package com.expensemanager.expensemanager.responseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorObject extends BaseResponseObject {

    private String message;

    public ErrorObject(Integer statusCode, String message) {
        super(statusCode);
        this.message = message;
    }
}
