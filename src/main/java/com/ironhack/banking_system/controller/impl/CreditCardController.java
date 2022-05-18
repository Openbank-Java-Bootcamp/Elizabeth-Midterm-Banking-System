package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.CreditCardControllerInterface;
import com.ironhack.banking_system.model.Checking;
import com.ironhack.banking_system.model.CreditCard;
import com.ironhack.banking_system.service.interfaces.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class CreditCardController implements CreditCardControllerInterface {

    @Autowired
    private CreditCardServiceInterface creditCardService;



    @PostMapping("/accounts/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCreditCard(@RequestBody CreditCard creditCard) {
        creditCardService.saveCreditCard(creditCard);
    }


}
