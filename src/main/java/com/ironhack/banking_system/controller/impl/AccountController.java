package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.controller.interfaces.AccountControllerInterface;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.model.Money;
import com.ironhack.banking_system.service.interfaces.AccountServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class AccountController implements AccountControllerInterface {

    @Autowired
    private AccountServiceInterface accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(@RequestBody AccountDTO accountDTO) {
        accountService.saveAccount(accountDTO);
    }

    @GetMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getAccountBalance(@PathVariable(name = "id") Long accountId) {
        return accountService.getAccountBalance(accountId);
    }

    @PatchMapping("/balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountBalance(@PathVariable(name = "id") Long accountId, @RequestBody @Valid AccountBalanceOnlyDTO accountBalanceOnlyDTO) {
        accountService.updateAccountBalance(accountId, accountBalanceOnlyDTO);
    }
}
