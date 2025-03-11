package com.banking.application.controller;

import com.banking.application.exception.AccountNotFoundException;
import com.banking.application.exception.InsufficientBalanceException;
import com.banking.application.model.BankAccount;
import com.banking.application.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    private BankAccountService service;

    @PostMapping("/accounts")
    public BankAccount createAccount(@RequestBody BankAccount account) {
        return service.createAccount(
                account.getAccountNumber(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }

    @GetMapping("/accounts/{accountNumber}")
    public BankAccount getAccount(@PathVariable String accountNumber) {
        return service.getAccount(accountNumber);
    }

    /*
     * TODO: Task 2 - Complete the POST /transfer endpoint
     * Requirements:
     * 1. Call the transfer method in BankAccountService with the provided parameters
     * 2. Return a success response (200 OK with a message like "Transfer completed")
     * 3. Handle exceptions:
     *    - AccountNotFoundException: Return 404 Not Found with an error message
     *    - InsufficientBalanceException: Return 400 Bad Request with an error message
     *    (Exception handlers are already provided below)
     */
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        // TODO: Implement this method
        return ResponseEntity.ok("Transfer completed");
    }

    // Exception handlers
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private double amount;

    // Getters and setters
    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}