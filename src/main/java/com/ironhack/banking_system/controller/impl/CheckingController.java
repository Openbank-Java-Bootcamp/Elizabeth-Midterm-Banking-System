package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.CheckingControllerInterface;
import com.ironhack.banking_system.service.interfaces.CheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class CheckingController implements CheckingControllerInterface {

    @Autowired
    private CheckingServiceInterface checkingService;


}
