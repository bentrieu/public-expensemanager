package com.expensemanager.expensemanager.Dto;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import lombok.Data;

import java.util.List;
@Data
public class TransactionPagination {
    private List<TransactionDto> transactions;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
