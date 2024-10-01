//package com.expensemanager.expensemanager.service;
//
//
//import com.expensemanager.expensemanager.model.Transaction;
//import com.expensemanager.expensemanager.repository.TransactionRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//public class TransactionServiceTests {
//
//    @InjectMocks
//    TransactionService transactionService;
//
//    @Mock
//    TransactionRepository transactionRepository;
//
//    @BeforeEach
//    public void setUp() {
//        Transaction transaction = Transaction.builder()
//                .name("test")
//                .amount(50.00)
//                .category("Drink")
//                .source("Credit")
//                .build();
//        SecurityContextHolder.getContext().getAuthentication()
//    }
//
//    @Test
//    public void transactionService_saveTransaction_ShouldReturnSavedTransaction() {
//
//        when(transactionRepository.save())
//    }
//
//}
