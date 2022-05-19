package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.repository.CheckingRepository;
import com.ironhack.banking_system.repository.TransferRepository;
import com.ironhack.banking_system.service.interfaces.TransferServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class TransferService implements TransferServiceInterface {

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    CheckingRepository checkingRepository;



    @Autowired
    AccountRepository accountRepository;


    public Transfer saveTransfer(TransferDTO transferDTO) {
        Long originAccountId = transferDTO.getOriginAccountId();
        Long destinationAccountId = transferDTO.getDestinationAccountId();
        Money funds = transferDTO.getFunds();
        Optional<Account> originAccount = accountRepository.findById(originAccountId);
        if (originAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin account does not exist");
        } else {
            Optional<Account> destinationAccount = accountRepository.findById(destinationAccountId);
            if (destinationAccount.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account does not exist");
            } else {
                String DTOName = transferDTO.getDestinationAccountOwner();
                String primaryOwnerName = destinationAccount.get().getPrimaryOwner().getName();
                String secondaryOwnerName = destinationAccount.get().getSecondaryOwner().getName();
                if (!DTOName.equals(primaryOwnerName) && !DTOName.equals(secondaryOwnerName)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name provided does not match owner of destination account.");
                }
                //downcast to specific account types
                //origin account:
                if (originAccount.get() instanceof CreditCard) {
                    try {
                        ((CreditCard) originAccount.get()).debitAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (originAccount.get() instanceof Checking) {
                    try {
                        ((Checking) originAccount.get()).debitAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (originAccount.get() instanceof StudentChecking) {
                    try {
                        ((StudentChecking) originAccount.get()).debitAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (originAccount.get() instanceof Savings) {
                    try {
                        ((Savings) originAccount.get()).debitAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type not found");
                }
                //destination accounts:
                if (destinationAccount.get() instanceof CreditCard) {
                    try {
                        ((CreditCard) destinationAccount.get()).creditAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (destinationAccount.get() instanceof Checking) {
                    try {
                        ((Checking) destinationAccount.get()).creditAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (destinationAccount.get() instanceof StudentChecking) {
                    try {
                        ((StudentChecking) destinationAccount.get()).creditAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else if (destinationAccount.get() instanceof Savings) {
                    try {
                        ((Savings) destinationAccount.get()).creditAccount(funds);
                    } catch (IllegalArgumentException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type not found");
                }

                return transferRepository.save(new Transfer(originAccount.get(), destinationAccount.get(), funds));
            }
        }
    }
}
