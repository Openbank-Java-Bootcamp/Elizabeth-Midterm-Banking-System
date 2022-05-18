package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.service.interfaces.AccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
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
