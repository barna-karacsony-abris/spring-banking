package com.banking.application.service;

import com.banking.application.exception.AccountNotFoundException;
import com.banking.application.exception.InsufficientBalanceException;
import com.banking.application.model.BankAccount;
import com.banking.application.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService service;

    @Autowired
    private BankAccountRepository repository;

    private BankAccount sourceAccount;
    private BankAccount destinationAccount;

    @BeforeEach
    public void setup() {
        // Clean up the repository
        repository.deleteAll();

        // Create test accounts
        sourceAccount = service.createAccount("SOURCE123", "Source Account", 500.0);
        destinationAccount = service.createAccount("DEST456", "Destination Account", 200.0);
    }

    @Test
    public void testSuccessfulTransfer() {
        // Perform transfer
        service.transfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), 100.0);

        // Verify the balances after transfer
        BankAccount updatedSource = service.getAccount(sourceAccount.getAccountNumber());
        BankAccount updatedDest = service.getAccount(destinationAccount.getAccountNumber());

        assertEquals(400.0, updatedSource.getBalance(), 0.001);
        assertEquals(300.0, updatedDest.getBalance(), 0.001);
    }

    @Test
    public void testTransferWithNonExistentAccount() {
        // Test with non-existent source account
        assertThrows(AccountNotFoundException.class, () -> {
            service.transfer("NON_EXISTENT", destinationAccount.getAccountNumber(), 100.0);
        });

        // Test with non-existent destination account
        assertThrows(AccountNotFoundException.class, () -> {
            service.transfer(sourceAccount.getAccountNumber(), "NON_EXISTENT", 100.0);
        });
    }

    @Test
    public void testTransferWithInsufficientBalance() {
        // Test with insufficient balance
        assertThrows(InsufficientBalanceException.class, () -> {
            service.transfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), 600.0);
        });

        // Verify the balances remain unchanged
        BankAccount updatedSource = service.getAccount(sourceAccount.getAccountNumber());
        BankAccount updatedDest = service.getAccount(destinationAccount.getAccountNumber());

        assertEquals(500.0, updatedSource.getBalance(), 0.001);
        assertEquals(200.0, updatedDest.getBalance(), 0.001);
    }
}