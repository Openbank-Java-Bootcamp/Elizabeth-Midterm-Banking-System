package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.AccountHolderControllerInterface;
import com.ironhack.banking_system.service.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    private AccountHolderServiceInterface accountHolderService;


}
