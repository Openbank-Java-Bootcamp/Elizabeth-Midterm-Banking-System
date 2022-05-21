package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.repository.ThirdPartyRepository;
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
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    AccountRepository accountRepository;


//    public Transfer saveTransfer(Long originAccountId, TransferDTO transferDTO) {
//        //Long originAccountId = transferDTO.getOriginAccountId();
//        Long destinationAccountId = transferDTO.getDestinationAccountId();
//        Money funds = transferDTO.getFunds();
//        Account originAccount = accountRepository.findById(originAccountId);
//        if (originAccount == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin account does not exist");
//        } else {
//            Account destinationAccount = accountRepository.findById(destinationAccountId);
//            if (destinationAccount == null) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account does not exist");
//            } else {
//                String DTOName = transferDTO.getDestinationAccountOwner();
//                String primaryOwnerName = destinationAccount.getPrimaryOwner().getName();
//                String secondaryOwnerName = destinationAccount.getSecondaryOwner().getName();
//                if (!DTOName.equals(primaryOwnerName) && !DTOName.equals(secondaryOwnerName)) {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name provided does not match owner of destination account.");
//                } else {
//                //downcast to specific account types
//                //origin account:
//                if (originAccount instanceof CreditCard) {
//                    try {
//                        ((CreditCard) originAccount).debitAccount(funds);
//                    } catch (IllegalArgumentException e) {
//                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
//                    }
//                } else if (originAccount instanceof Checking) {
//                    try {
//                        ((Checking) originAccount).debitAccount(funds);
//                    } catch (IllegalArgumentException e) {
//                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
//                    }
//                } else if (originAccount instanceof StudentChecking) {
//                    try {
//                        ((StudentChecking) originAccount).debitAccount(funds);
//                    } catch (IllegalArgumentException e) {
//                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
//                    }
//                } else if (originAccount instanceof Savings) {
//                    try {
//                        ((Savings) originAccount).debitAccount(funds);
//                    } catch (IllegalArgumentException e) {
//                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
//                    }
//                } else {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type not found");
//                }
//                //destination accounts:
//                if (destinationAccount instanceof CreditCard) {
//                    ((CreditCard) destinationAccount).creditAccount(funds);
//
//                } else if (destinationAccount instanceof Checking) {
//                    ((Checking) destinationAccount).creditAccount(funds);
//                } else if (destinationAccount instanceof StudentChecking) {
//                    ((StudentChecking) destinationAccount).creditAccount(funds);
//                } else if (destinationAccount instanceof Savings) {
//                    ((Savings) destinationAccount).creditAccount(funds);
//                } else {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type not found");
//                }
//
//                return transferRepository.save(new InternalTransfer(funds, originAccount, destinationAccount));
//            }}
//        }
//    }

//    public Transfer saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {
//        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);
//        if (thirdParty.getId() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third Party not in database.");
//        } else {
//            Long accountId = transferThirdPartyDTO.getAccountId();
//            Account destinationAccount = accountRepository.findById(accountId);
//            if (!transferThirdPartyDTO.getAccountSecretKey().equals(destinationAccount.getSecretKey())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret key invalid.");
//            } else {
//                Money funds = transferThirdPartyDTO.getFunds();
//                if (destinationAccount instanceof CreditCard) {
//                    ((CreditCard) destinationAccount).creditAccount(funds);
//                } else if (destinationAccount instanceof Checking) {
//                    ((Checking) destinationAccount).creditAccount(funds);
//                } else if (destinationAccount instanceof StudentChecking) {
//                    ((StudentChecking) destinationAccount).creditAccount(funds);
//                } else if (destinationAccount instanceof Savings) {
//                    ((Savings) destinationAccount).creditAccount(funds);
//                } else {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account type not found");
//                }
//                return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, destinationAccount));
//            }
//        }
//    }

//    public Transfer saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {
//        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);
//        if (thirdParty.getId() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third Party not in database.");
//        } else {
//            Long accountId = transferThirdPartyDTO.getAccountId();
//            Account originAccount = accountRepository.findById(accountId);
//            if (!transferThirdPartyDTO.getAccountSecretKey().equals(originAccount.getSecretKey())) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret key invalid.");
//            } else {
//                Money funds = transferThirdPartyDTO.getFunds();
//                try {
//                    originAccount.debitAccount(funds);
//                    accountRepository.save(originAccount);
//                    return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, originAccount));
//                } catch (IllegalArgumentException e) {
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
//                }
//            }
//        }
//    }
}
