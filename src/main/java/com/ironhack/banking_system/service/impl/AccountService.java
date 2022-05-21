package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.service.interfaces.AccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
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
        return accountRepository.findById(id).get();
    }

    public void updateAccountBalance(Long accountId, AccountBalanceOnlyDTO accountBalanceOnlyDTO) {
        Account account = accountRepository.findById(accountId).get();
        account.setBalance(accountBalanceOnlyDTO.getBalance());
        accountRepository.save(account);
    }



//    Account saveAccount(AccountHolder primaryAccountHolder, Optional<AccountHolder> secondaryAccountHolder, String accountType, String secretKey) {
//        if (accountType.toUpperCase().equals("CHECKING")) {
//            if (primaryAccountHolder.calculateAge() <24) {
//                Account newStudentChecking = new StudentChecking(primaryAccountHolder, secondaryAccountHolder.get(), secretKey);
//                accountRepository.save(newStudentChecking);
//                return newStudentChecking;
//            } else {
//                Account newChecking = new Checking(primaryAccountHolder, secondaryAccountHolder.get(), secretKey);
//                accountRepository.save(newChecking);
//                return newChecking;
//            }
//        } else if (accountType.toUpperCase().equals("SAVINGS")) {
//            Account newSavings = new Savings(primaryAccountHolder, secondaryAccountHolder.get(), secretKey);
//            accountRepository.save(newSavings);
//            return newSavings;
//        } else if (accountType.toUpperCase().equals("CREDITCARD")) {
//            Account newCreditCard = new CreditCard(primaryAccountHolder, secondaryAccountHolder.get());
//            accountRepository.save(newCreditCard);
//            return newCreditCard;
//        } else {
//
//        }
//    }

//    Account saveAccount(Account account, String accountType) {
//        Optional<AccountHolder> foundPrimaryOwner = accountHolderRepository.findById(account.getPrimaryOwner().getId());
//        if(foundPrimaryOwner.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid primary owner.");
//        }
//        try {
//            if (accountType.toUpperCase().equals("CHECKING")) {
//                if (foundPrimaryOwner.get().calculateAge() < 24) {
//                    return accountRepository.save(new StudentChecking(
//                           account.getPrimaryOwner(),
//                            account.getSecondaryOwner(),
//                            account.getSecretKey
//                    ))
//                }
//
//            }
//
//
//        }
//    }
}
