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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    //public InternalTransfer saveInternalTransfer(Long originAccountId,TransferDTO transferDTO) {
    public InternalTransfer saveInternalTransfer(TransferDTO transferDTO) {
        //get username from token
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String tokenUsername = "";
        if (principal instanceof UserDetails) {
            tokenUsername = ((UserDetails) principal).getUsername();
        } else {
            tokenUsername = principal.toString();
        }
        //find sending account
        Optional<Account> originAccount = accountRepository.findById(transferDTO.getOriginAccountId());
        if (originAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Sending Account");
        }
        //check if user is an account owner
        String primaryUsername = originAccount.get().getPrimaryOwner().getUsername();
        if (!tokenUsername.equals(primaryUsername)) {  //if primary username does not match token username
            try {
                String secondaryUsername = originAccount.get().getSecondaryOwner().getUsername();
                if (!tokenUsername.equals(secondaryUsername)) { // if secondary owner exists and does not match token
                   throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not an account owner of sending account");
                }
            } catch (NullPointerException e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not an account owner of sending account");
            }
        }
        //find receiving account
        Long destinationId = transferDTO.getDestinationAccountId();
        Optional<Account> destinationAccount = accountRepository.findById(destinationId);
        if (destinationAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Receiving Account");
        }
        //check if name is one of receiving account's owners
        String primaryName = destinationAccount.get().getPrimaryOwner().getName();
        if (!transferDTO.getDestinationAccountOwner().equals(primaryName)) {
            try {
                String secondaryName = destinationAccount.get().getSecondaryOwner().getName();
                if (!transferDTO.getDestinationAccountOwner().equals(secondaryName)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name on transfer does not match receiving account owner.");
                }
            } catch (NullPointerException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name on transfer does not match receiving account owner.");
            }
        }
        //make transfer
        try {
            Money funds = transferDTO.getFunds();
            originAccount.get().debitAccount(funds);
            destinationAccount.get().creditAccount(funds);
            accountRepository.save(originAccount.get());
            accountRepository.save(destinationAccount.get());
            return transferRepository.save(new InternalTransfer(funds, originAccount.get(), destinationAccount.get()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient Funds");
        }
    }

    public ThirdPartyTransfer saveTransferFromThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {
        //check if third party is in database
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);
        if (thirdParty == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third Party not in database.");
        }
        //find account and match secret key
        Long accountId = transferThirdPartyDTO.getAccountId();
        Optional<Account> destinationAccount = accountRepository.findById(accountId);
        if (destinationAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account id");
        }
        if (!transferThirdPartyDTO.getAccountSecretKey().equals(destinationAccount.get().getSecretKey())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret key invalid.");
        }
        //credit account
        Money funds = transferThirdPartyDTO.getFunds();
        if (destinationAccount.get() instanceof CreditCard) {
            ((CreditCard) destinationAccount.get()).creditAccount(funds);
            accountRepository.save(destinationAccount.get());
            return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, destinationAccount.get()));
        } else {
            destinationAccount.get().creditAccount(funds);
            accountRepository.save(destinationAccount.get());
            return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, destinationAccount.get()));
        }
    }


    public ThirdPartyTransfer saveTransferToThirdParty(String hashedKey, TransferThirdPartyDTO transferThirdPartyDTO) {
        //check if third party in database
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);
        if (thirdParty == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Third Party not in database.");
        }
        //find account and match to secret key
        Long accountId = transferThirdPartyDTO.getAccountId();
        Optional<Account> originAccount = accountRepository.findById(accountId);
        if (originAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account id");
        }
        if (!transferThirdPartyDTO.getAccountSecretKey().equals(originAccount.get().getSecretKey())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret key invalid.");
        }
        //debit account
        Money funds = transferThirdPartyDTO.getFunds();
        try {
            if (originAccount.get() instanceof CreditCard) {
                ((CreditCard) originAccount.get()).debitAccount(funds);
                accountRepository.save(originAccount.get());
                return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, originAccount.get()));
            } else {
                originAccount.get().debitAccount(funds);
                accountRepository.save(originAccount.get());
                return transferRepository.save(new ThirdPartyTransfer(funds, thirdParty, originAccount.get()));
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds.");
        }
    }

}


