package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.DTO.CheckingDTO;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.CheckingRepository;
import com.ironhack.banking_system.repository.StudentCheckingRepository;
import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
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
public class CheckingService implements CheckingServiceInterface {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Checking saveChecking(CheckingDTO checkingDTO) {
        Optional<AccountHolder> foundPrimaryOwner = accountHolderRepository.findById(checkingDTO.getPrimaryOwnerId());
        Optional<AccountHolder> foundSecondaryOwner = accountHolderRepository.findById(checkingDTO.getSecondaryOwnerId());
        if (foundPrimaryOwner.isEmpty() || foundSecondaryOwner.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Primary or Secondary Account Owner not found in database.");
        } else {
            if (foundPrimaryOwner.get().calculateAge() <= 24) {
                try {
                    return studentCheckingRepository.save(new StudentChecking(
                                    foundPrimaryOwner.get(),
                                    foundSecondaryOwner.get(),
                                    checkingDTO.getBalance(),
                                    checkingDTO.getSecretKey()
                            )
                    );
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Savings Account");
                }
            } else {
                try {
                    return checkingRepository.save(new Checking(
                            foundPrimaryOwner.get(),
                            foundSecondaryOwner.get(),
                            checkingDTO.getBalance(),
                            checkingDTO.getSecretKey()
                    ));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Savings Account");
                }
            }
        }
    }

    public Money getCheckingBalance(Long id) {
        Checking checking = checkingRepository.getById(id);
        log.info("Fetching balance for checking account {}", id);
        return checking.getBalance();
    }

}
