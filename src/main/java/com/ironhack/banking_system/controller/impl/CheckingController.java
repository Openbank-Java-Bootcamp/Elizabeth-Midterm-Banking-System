package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.CheckingControllerInterface;
import com.ironhack.banking_system.model.Checking;
import com.ironhack.banking_system.model.Money;
import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class CheckingController implements CheckingControllerInterface {

    @Autowired
    private CheckingServiceInterface checkingService;


    @PostMapping("/accounts/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveChecking(@RequestBody Checking checking) {
        checkingService.saveChecking(checking);
    }

    @GetMapping("/accounts/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Money getCheckingBalance(@RequestBody Long id) {
        return checkingService.getCheckingBalance(id);
    }


}
