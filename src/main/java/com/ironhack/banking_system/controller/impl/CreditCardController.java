package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.CreditCardControllerInterface;
import com.ironhack.banking_system.service.interfaces.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class CreditCardController implements CreditCardControllerInterface {

    @Autowired
    private CreditCardServiceInterface creditCardService;


}
