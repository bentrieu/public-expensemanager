package com.expensemanager.expensemanager.responseObject;

import com.expensemanager.expensemanager.Dto.TransactionPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionPaginationResponse extends BaseResponseObject {

    private TransactionPagination transactionPagination;

    public TransactionPaginationResponse(Integer statusCode, TransactionPagination transactionPagination) {
        super(statusCode);
        this.transactionPagination = transactionPagination;
    }
}
