package com.expensemanager.expensemanager.repository;

import com.expensemanager.expensemanager.exceptions.TransactionNotFoundException;
import com.expensemanager.expensemanager.model.Transaction;
import com.expensemanager.expensemanager.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

@DataJpaTest

@AutoConfigureTestDatabase
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TransactionRepository_Save_ReturnSavedTransaction() {
        // Arrange
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("abcdef")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();

        // Act
        Transaction savedTransaction1 = transactionRepository.save(transaction);
        Transaction savedTransaction2 = transactionRepository.save(transaction2);

        // Assert
        Assertions.assertNotEquals(savedTransaction1.getId(), savedTransaction2.getId()); // Ensure IDs are unique and assigned
        Assertions.assertEquals(transaction, savedTransaction1);
        Assertions.assertEquals(transaction2, savedTransaction2);
    }


    @Test
    public void TransactionRepository_FindAll_ReturnMoreThanOneTransaction() {
        //Arrange
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();

        //Act
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        List<Transaction> result = transactionRepository.findAll();

        //Assert
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void TransactionRepository_FindById_ReturnCorrectTransaction() {
        //Arrange
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();

        final int transaction2Id = 2;
        //Act
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        Transaction result = transactionRepository.findById(transaction2Id).orElseThrow(() -> new TransactionNotFoundException("Cannot find transaction"));

        //Assert
        Assertions.assertEquals(transaction2, result);
    }

    @Test
    public void TransactionRepository_UpdateTransaction_ReturnUpdatedTransaction() {
        //Arrange
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();
        //Act
        Transaction result = transactionRepository.save(transaction);

        result.setName("New name");

        Transaction updatedTransaction = transactionRepository.save(result);
        //Assert
        Assertions.assertEquals(result, updatedTransaction);
    }

    @Test
    public void TransactionRepository_SaveThenDelete_ReturnNoTransaction() {
        //Arrange
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .build();

        final int expectedSize = 0;
        //Act
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        transactionRepository.delete(transaction);
        transactionRepository.delete(transaction2);

        List<Transaction> transactionList = transactionRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedSize, transactionList.size());
    }

    @Test
    public void TransactionRepository_FindAllByUserId_ReturnPageableWithSavedTransactions() {
        //Arrange
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email("email")
                .password("password")
                .build();

        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .user(user)
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .user(user)
                .build();
        final int pageNumber = 0;
        final int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Transaction> expectedList = Arrays.asList(transaction, transaction2);
        user.setTransactions(expectedList);

        //Act
        userRepository.save(user);
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        Page<Transaction> result = transactionRepository.findAllByUserId(pageable, user.getId());
        List<Transaction> resultList = result.getContent();
        //Assert
        Assertions.assertEquals(expectedList, resultList);
    }

    @Test
    public void TransactionRepository_FindAllByUserId_ReturnPageableWithTwoElements() {
        //Arrange
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email("email")
                .password("password")
                .build();
        Transaction transaction = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .user(user)
                .build();
        Transaction transaction2 = Transaction.builder()
                .name("test")
                .amount(50.00)
                .category("Drink")
                .source("Credit")
                .user(user)
                .build();
        final int pageNumber = 0;
        final int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Transaction> expectedList = Arrays.asList(transaction, transaction2);
        int expectedSize = expectedList.size();
        user.setTransactions(expectedList);

        //Act
        userRepository.save(user);
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        Page<Transaction> result = transactionRepository.findAllByUserId(pageable, user.getId());
        Long resultSize = result.getTotalElements();

        //Assert
        Assertions.assertEquals(expectedSize, resultSize);
    }
}
