package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.Checking;
import com.ironhack.banking_system.model.Money;
import com.ironhack.banking_system.repository.CheckingRepository;
import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckingService implements CheckingServiceInterface {

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Checking saveChecking(Checking checking) {
        //or take accountHolder as a parameter???
        log.info("Saving new checking account {} inside of the database", checking.getId());
        checking.setSecretKey(passwordEncoder.encode(checking.getSecretKey())); //encode secretKey???
        return checkingRepository.save(checking);
    }

    public Money getCheckingBalance(Long id) {
        Checking checking = checkingRepository.getById(id);
        log.info("Fetching balance for checking account {}", id);
        return checking.getBalance();
    }

}
