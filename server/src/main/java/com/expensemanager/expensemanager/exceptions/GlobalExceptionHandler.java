package com.expensemanager.expensemanager.exceptions;

import com.expensemanager.expensemanager.responseObject.BaseResponseObject;
import com.expensemanager.expensemanager.responseObject.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Add any custom exception here using @ExceptionHandler(exception.class), build using errorobject
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<BaseResponseObject> handleTransactionNotFoundException(TransactionNotFoundException ex, WebRequest req) {
        BaseResponseObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
