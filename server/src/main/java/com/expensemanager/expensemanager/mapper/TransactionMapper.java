package com.expensemanager.expensemanager.mapper;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import com.expensemanager.expensemanager.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto mapToDto(Transaction transaction);
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    @Mapping(target = "id", ignore = true)
    Transaction mapToModel(TransactionDto transactionDto);
}
