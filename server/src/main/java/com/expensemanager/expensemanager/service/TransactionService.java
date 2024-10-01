package com.expensemanager.expensemanager.service;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import com.expensemanager.expensemanager.Dto.TransactionPagination;

public interface TransactionService {

    //create
    public TransactionDto createTransaction(TransactionDto transactionDto);
    //get
    public TransactionDto getTransactionById(int id);

    //get all
    public TransactionPagination getAllTransaction(int pageNo, int pageSize);
    //update
    public TransactionDto updateTransaction(TransactionDto transactionDto, int id);
    //delete
    public void deleteTransaction(int id);
}
