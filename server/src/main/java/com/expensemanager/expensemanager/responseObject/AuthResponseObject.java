package com.expensemanager.expensemanager.responseObject;

import com.expensemanager.expensemanager.Dto.AuthResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthResponseObject extends BaseResponseObject{

    private AuthResponseDto authResponseDto;

    public AuthResponseObject(Integer statusCode, AuthResponseDto authResponseDto) {
        super(statusCode);
        this.authResponseDto = authResponseDto;
    }
}
