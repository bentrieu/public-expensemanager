package com.expensemanager.expensemanager.controller;

import com.expensemanager.expensemanager.Dto.TransactionDto;
import com.expensemanager.expensemanager.Dto.TransactionPagination;
import com.expensemanager.expensemanager.responseObject.StringResponseObject;
import com.expensemanager.expensemanager.responseObject.TransactionPaginationResponse;
import com.expensemanager.expensemanager.responseObject.TransactionResponseObject;
import com.expensemanager.expensemanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions") // TODO: switch to api/v1/user/{userId}/transactions later when implemented user
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseObject> createTransaction(@RequestBody TransactionDto transactionDto) {
        TransactionDto result = transactionService.createTransaction(transactionDto);
        TransactionResponseObject responseObject = new TransactionResponseObject(HttpStatus.CREATED.value(), result);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseObject> getTransactionById(@PathVariable int id) {
        TransactionDto result = transactionService.getTransactionById(id);
        TransactionResponseObject responseObject = new TransactionResponseObject(HttpStatus.OK.value(), result);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TransactionPaginationResponse> getAllTransaction(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") int pageSize
    ) {
        TransactionPagination result = transactionService.getAllTransaction(pageNo, pageSize);
        TransactionPaginationResponse responseObject = new TransactionPaginationResponse(HttpStatus.OK.value(), result);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseObject> updateTransaction(@RequestBody TransactionDto transactionDto, @PathVariable int id) {
        TransactionDto result = transactionService.updateTransaction(transactionDto, id);
        TransactionResponseObject responseObject = new TransactionResponseObject(HttpStatus.OK.value(), result);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StringResponseObject> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        StringResponseObject responseObject = new StringResponseObject(HttpStatus.OK.value(), "Transaction deleted");
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
