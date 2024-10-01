package com.expensemanager.expensemanager.service.impl;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import com.expensemanager.expensemanager.Dto.TransactionPagination;
import com.expensemanager.expensemanager.exceptions.TransactionNotFoundException;
import com.expensemanager.expensemanager.mapper.TransactionMapper;
import com.expensemanager.expensemanager.model.Transaction;
import com.expensemanager.expensemanager.model.UserEntity;
import com.expensemanager.expensemanager.repository.TransactionRepository;
import com.expensemanager.expensemanager.repository.UserRepository;
import com.expensemanager.expensemanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        UserEntity user = getCurrentUser();

        Transaction transaction = transactionMapper.mapToModel(transactionDto);

        transaction.setUser(user);

        Transaction result = transactionRepository.save(transaction);

        return transactionMapper.mapToDto(result);
    }

    @Override
    public TransactionDto getTransactionById(int transactionId) {
        UserEntity user = getCurrentUser();

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(String.format("Couldn't get the transaction with id: %d",transactionId)));

        boolean isUserIdMatch = checkUserId(user.getId(), transaction.getUser().getId());
        if(!isUserIdMatch) {
            throw new TransactionNotFoundException(String.format("User: %d does not have transaction %d",user.getId(),transactionId));
        }

        return transactionMapper.mapToDto(transaction);
    }

    @Override
    public TransactionPagination getAllTransaction(int pageNo, int pageSize) {
        UserEntity user = getCurrentUser();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Transaction> transactions = transactionRepository.findAllByUserId(pageable, user.getId());
        List<Transaction> transactionList = transactions.getContent();
        List<TransactionDto> transactionDtosList = transactionList.stream().map((p) -> transactionMapper.mapToDto(p)).toList();
        TransactionPagination transactionResponse = new TransactionPagination();

        transactionResponse.setTransactions(transactionDtosList);
        transactionResponse.setPageNo(pageable.getPageNumber());
        transactionResponse.setPageSize(pageable.getPageSize());
        transactionResponse.setTotalElements(transactions.getTotalElements());
        transactionResponse.setTotalPages(transactions.getTotalPages());
        transactionResponse.setLast(transactions.isLast());

        return transactionResponse;
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto, int transactionId) {
        UserEntity user = getCurrentUser();

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(String.format("Couldn't find the transaction with id: %d",transactionId)));

        boolean isUserIdMatch = checkUserId(user.getId(), transaction.getUser().getId());
        if(!isUserIdMatch) {
            throw new TransactionNotFoundException(String.format("User: %d does not have transaction %d",user.getId(),transactionId));
        }

        transaction.setName(transactionDto.getName());
        transaction.setCategory(transactionDto.getCategory());
        transaction.setSource(transactionDto.getSource());
        transaction.setAmount(transactionDto.getAmount());

        Transaction result = transactionRepository.save(transaction);;

        return transactionMapper.mapToDto(result);
    }

    @Override
    public void deleteTransaction(int transactionId) {
        UserEntity user = getCurrentUser();

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(String.format("Couldn't delete the transaction with id: %d",transactionId)));

        boolean isUserIdMatch = checkUserId(user.getId(), transaction.getUser().getId());

        if(!isUserIdMatch) {
            throw new TransactionNotFoundException(String.format("User %d does not have transaction %d", user.getId(), transactionId));
        }

        transactionRepository.delete(transaction);
    }

    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not authenticated"));
    }

    public boolean checkUserId(int userId, int transactionUserId) {
        return userId == transactionUserId;
    }

}
