package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountStatusOnlyDTO;
import com.ironhack.banking_system.controller.interfaces.SavingsControllerInterface;
import com.ironhack.banking_system.model.Account;
import com.ironhack.banking_system.model.Savings;
import com.ironhack.banking_system.service.interfaces.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/accounts/savings")
public class SavingsController implements SavingsControllerInterface {

    @Autowired
    private SavingsServiceInterface savingsService;



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Savings getSavings(@PathVariable Long id) {
        return savingsService.getSavingsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveSavings(@RequestBody Savings savings) {
        savingsService.saveSavings(savings);
    }

    @PatchMapping("/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partialUpdateBalance(@PathVariable Long id, @RequestBody AccountBalanceOnlyDTO partialAccount) {
        savingsService.updateBalance(id, partialAccount.getBalance());
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void partialUpdateStatus(@PathVariable Long id, @RequestBody AccountStatusOnlyDTO partialAccount) {
        savingsService.updateStatus(id, partialAccount.getStatus());
    }



//    @PostMapping("/accounts/savings")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveSavings(@RequestParam String primaryAccountOwner, double balance, String secretKey) {
//
//    }
}
