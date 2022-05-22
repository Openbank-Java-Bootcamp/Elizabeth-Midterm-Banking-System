//package com.ironhack.banking_system.controller.impl;
//
//import com.ironhack.banking_system.DTO.CheckingDTO;
//import com.ironhack.banking_system.controller.interfaces.CheckingControllerInterface;
//import com.ironhack.banking_system.model.Checking;
//import com.ironhack.banking_system.model.Money;
//import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/bank")
//public class CheckingController implements CheckingControllerInterface {
//
//    @Autowired
//    private CheckingServiceInterface checkingService;
//
//
//
//    @PostMapping("/accounts/checking")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveChecking(@RequestBody CheckingDTO checkingDTO) {
//        checkingService.saveChecking(checkingDTO);
//    }
//
//    @GetMapping("/accounts/checking/balance/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Money getCheckingBalance(@PathVariable Long id) {
//        return checkingService.getCheckingBalance(id);
//    }
//
//
//}
