package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.repository.CreditCardRepository;
import com.ironhack.banking_system.service.interfaces.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService implements CreditCardServiceInterface {

    @Autowired
    private CreditCardRepository creditCardRepository;

}
