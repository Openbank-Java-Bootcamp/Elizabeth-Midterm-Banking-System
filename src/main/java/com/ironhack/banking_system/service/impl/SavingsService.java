package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.SavingsRepository;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingsService implements SavingsServiceInterface {

    @Autowired
    private SavingsRepository savingsRepository;
}
