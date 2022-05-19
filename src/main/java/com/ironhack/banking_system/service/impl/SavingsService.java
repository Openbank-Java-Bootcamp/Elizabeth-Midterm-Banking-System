package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.enums.AccountStatus;
import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.Money;
import com.ironhack.banking_system.model.Savings;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.SavingsRepository;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class SavingsService implements SavingsServiceInterface {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    public Savings saveSavings(Savings savings) {
//        log.info("Saving new savings account {} inside of the database", savings.getId());
//        savings.setSecretKey(passwordEncoder.encode(savings.getSecretKey())); //encode secretKey???
//        return savingsRepository.save(savings);
//    }

    public Savings getSavingsById(Long id) {
        //get username from token
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String tokenUsername = "";
        if (principal instanceof UserDetails) {
             tokenUsername = ((UserDetails)principal).getUsername();
        } else {
             tokenUsername = principal.toString();
        }
        //find savings account
        Optional<Savings> foundSavings = savingsRepository.findById(id);
        if (foundSavings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Savings Account with that ID");
        } else {
            String primaryUsername = foundSavings.get().getPrimaryOwner().getUsername();
            String secondaryUsername = foundSavings.get().getSecondaryOwner().getUsername();
            if (tokenUsername.equals(primaryUsername) || tokenUsername.equals(secondaryUsername)) {
                return foundSavings.get();
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access restricted to Account Owners");
            }
        }
    }

    public Savings saveSavings(Savings savings) {
        AccountHolder foundAccountHolder = accountHolderRepository.findByUsername(savings.getPrimaryOwner().getUsername());
        if (foundAccountHolder == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primary Account Owner not found in database.");
        }
        try {
            return savingsRepository.save(new Savings(
                    savings.getPrimaryOwner(),
                    savings.getSecondaryOwner(),
                    savings.getBalance(),
                    savings.getSecretKey(),
                    savings.getMinimumBalance(),
                    savings.getInterestRate()
                    )
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Savings Account");
        }
        //log.info("Saving new savings account {} to the database", savings.getId());
        //savings.setSecretKey(passwordEncoder.encode(savings.getSecretKey())); //encode secretKey???
        //return savingsRepository.save(savings);
    }

    public void updateBalance(Long id, Money balance) {
        Savings savingsFromDb = savingsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found"));
        savingsFromDb.setBalance(balance);
        savingsRepository.save(savingsFromDb);
    }

    public void updateStatus(Long id, AccountStatus status) {
        Savings savingsFromDb = savingsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found"));
        savingsFromDb.setStatus(status);
        savingsRepository.save(savingsFromDb);
    }
}
