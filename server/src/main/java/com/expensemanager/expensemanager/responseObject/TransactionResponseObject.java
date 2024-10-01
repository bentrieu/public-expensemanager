package com.expensemanager.expensemanager.responseObject;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionResponseObject extends BaseResponseObject {

    private TransactionDto transactionDto;

    public TransactionResponseObject(Integer statusCode, TransactionDto transactionDto) {
        super(statusCode);
        this.transactionDto = transactionDto;
    }
}
