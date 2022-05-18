package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.SavingsControllerInterface;
import com.ironhack.banking_system.model.Savings;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class SavingsController implements SavingsControllerInterface {

    @Autowired
    private SavingsServiceInterface savingsService;


    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSavings(@RequestBody Savings savings) {
        savingsService.saveSavings(savings);
    }
}
