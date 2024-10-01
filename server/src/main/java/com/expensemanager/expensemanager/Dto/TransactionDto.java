package com.expensemanager.expensemanager.Dto;


import lombok.Data;

@Data
public class TransactionDto {

    private int id;
    private float amount;
    private String name;
    private String category;
    private String source;
}
