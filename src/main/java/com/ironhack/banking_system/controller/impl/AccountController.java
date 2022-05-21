package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.controller.interfaces.AccountControllerInterface;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.service.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/accounts")
public class AccountController implements AccountControllerInterface {

    @Autowired
    private AccountServiceInterface accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(@RequestBody AccountDTO accountDTO) {
        accountService.saveAccount(accountDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PatchMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountBalance(@PathVariable Long accountId, @RequestBody AccountBalanceOnlyDTO accountBalanceOnlyDTO) {
        accountService.updateAccountBalance(accountId, accountBalanceOnlyDTO);
    }
}
