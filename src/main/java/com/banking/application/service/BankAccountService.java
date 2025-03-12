package com.banking.application.service;

import com.banking.application.exception.AccountNotFoundException;
import com.banking.application.model.BankAccount;
import com.banking.application.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    public BankAccount createAccount(String accountNumber, String accountHolderName, double balance) {
        BankAccount account = new BankAccount(accountNumber, accountHolderName, balance);
        return repository.save(account);
    }

    public BankAccount getAccount(String accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }

    /*
     * TODO: Task 1 - Implement the transfer method
     * Requirements:
     * 1. The method should be transactional
     * 2. Retrieve both source and destination accounts using the repository
     * 3. Check if both accounts exist; if not, throw AccountNotFoundException
     * 4. Verify that the source account has sufficient balance; if not, throw InsufficientBalanceException
     * 5. Update the balances: subtract amount from source account and add it to destination account
     * 6. Save both updated accounts using the repository
     */
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        // TODO: Implement this method
    }
}