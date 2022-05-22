package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.service.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public Account saveAccount(AccountDTO accountDTO) {
        Long primaryOwnerId = accountDTO.getPrimaryOwnerId();
        Long secondaryOwnerId = accountDTO.getSecondaryOwnerId();
        try {
            Optional<AccountHolder> primaryOwner = accountHolderRepository.findById(primaryOwnerId);
            AccountType accountType = accountDTO.getAccountType();
            if (accountType == AccountType.checking) {
                if (primaryOwner.get().calculateAge() < 24) {
                    Account newAccount = new StudentChecking(
                            accountDTO.getBalance(),
                            accountDTO.getSecretKey(),
                            primaryOwner.get()
                    );
                    if (secondaryOwnerId != null) {
                        Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                        newAccount.setSecondaryOwner(secondaryOwner.get());
                    }
                    accountRepository.save(newAccount);
                    return newAccount;
                } else {
                    Account newAccount = new Checking(
                            accountDTO.getBalance(),
                            accountDTO.getSecretKey(),
                            primaryOwner.get()
                    );
                    if (secondaryOwnerId != null) {
                        Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                        newAccount.setSecondaryOwner(secondaryOwner.get());
                    }
                    accountRepository.save(newAccount);
                    return newAccount;
                }
            }
            else if (accountType == AccountType.savings) {
                Account newAccount = new Savings(
                        accountDTO.getBalance(),
                        accountDTO.getSecretKey(),
                        primaryOwner.get(),
                        accountDTO.getMinimumBalance(),
                        accountDTO.getInterestRate()
                );
                if (secondaryOwnerId != null) {
                    Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                    newAccount.setSecondaryOwner(secondaryOwner.get());
                }
                accountRepository.save(newAccount);
                return newAccount;
            } else if (accountType == AccountType.creditcard) {
                Account newAccount = new CreditCard(
                        accountDTO.getBalance(),
                        accountDTO.getSecretKey(),
                        primaryOwner.get(),
                        accountDTO.getInterestRate(),
                        accountDTO.getCreditLimit()
                );
                if (secondaryOwnerId != null) {
                    Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(secondaryOwnerId);
                    newAccount.setSecondaryOwner(secondaryOwner.get());
                }
                accountRepository.save(newAccount);
                return newAccount;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account type");
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Account details.");
        }
    }


    public Account getAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.get() instanceof CreditCard) {
            ((CreditCard) account.get()).applyInterestIfApplicable();
            return accountRepository.save(account.get());
        } else if (account.get() instanceof Savings ){
            ((Savings) account.get()).applyInterestIfApplicable();
            return accountRepository.save(account.get());
        } else {
            return account.get();
        }
    }

    public Money getAccountBalance(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.get() instanceof CreditCard) {
            ((CreditCard) account.get()).applyInterestIfApplicable();
            accountRepository.save(account.get());
            return account.get().getBalance();
        } else if (account.get() instanceof Savings ){
            ((Savings) account.get()).applyInterestIfApplicable();
            accountRepository.save(account.get());
            return account.get().getBalance();
        } else {
            return account.get().getBalance();
        }
    }

    public void updateAccountBalance(Long accountId, AccountBalanceOnlyDTO accountBalanceOnlyDTO) {
        Account account = accountRepository.findById(accountId).get();
        account.setBalance(accountBalanceOnlyDTO.getBalance());
        accountRepository.save(account);
    }

}
