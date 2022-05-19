package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.AccountHolderControllerInterface;
import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.service.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/accountholders")
public class AccountHolderController implements AccountHolderControllerInterface {

    @Autowired
    private AccountHolderServiceInterface accountHolderService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolderById(@PathVariable Long id) {
        return accountHolderService.getAccountHolderById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAccountHolders() {
        return accountHolderService.getAccountHolders();
    }

    @PostMapping("/accountholders")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccountHolder(@RequestBody AccountHolder accountHolder) {
        accountHolderService.saveAccountHolder(accountHolder);
    }


}
