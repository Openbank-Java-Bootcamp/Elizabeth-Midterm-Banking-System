package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.CheckingRepository;
import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingService implements CheckingServiceInterface {

    @Autowired
    private CheckingRepository checkingRepository;

}
