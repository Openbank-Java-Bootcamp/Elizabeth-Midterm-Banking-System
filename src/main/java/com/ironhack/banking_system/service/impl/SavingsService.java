package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.Savings;
import com.ironhack.banking_system.repository.SavingsRepository;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SavingsService implements SavingsServiceInterface {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Savings saveSavings(Savings savings) {
        log.info("Saving new savings account {} inside of the database", savings.getId());
        savings.setSecretKey(passwordEncoder.encode(savings.getSecretKey())); //encode secretKey???
        return savingsRepository.save(savings);
    }
}
