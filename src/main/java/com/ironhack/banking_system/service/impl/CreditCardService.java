package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.CreditCard;
import com.ironhack.banking_system.repository.CreditCardRepository;
import com.ironhack.banking_system.service.interfaces.CreditCardServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreditCardService implements CreditCardServiceInterface {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public CreditCard saveCreditCard(CreditCard creditCard) {
        log.info("Saving new credit card account {} inside of the database", creditCard.getId());
        //creditCard.setSecretKey(passwordEncoder.encode(creditCard.getSecretKey())); //use secretKey???
        return creditCardRepository.save(creditCard);
    }


}
