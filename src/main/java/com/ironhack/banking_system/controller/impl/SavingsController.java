package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.SavingsControllerInterface;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class SavingsController implements SavingsControllerInterface {

    @Autowired
    private SavingsServiceInterface savingsService;
}
