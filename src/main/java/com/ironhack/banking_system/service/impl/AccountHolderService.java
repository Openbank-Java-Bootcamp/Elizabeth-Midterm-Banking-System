package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.service.interfaces.AccountHolderServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {

    @Autowired
    private AccountHolderRepository accountHolderRepository;


}
