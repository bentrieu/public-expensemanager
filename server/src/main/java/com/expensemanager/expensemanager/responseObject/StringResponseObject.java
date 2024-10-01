package com.expensemanager.expensemanager.responseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StringResponseObject extends BaseResponseObject {
    private String message;

    public StringResponseObject(Integer statusCode, String message) {
        super(statusCode);
        this.message = message;
    }
}
