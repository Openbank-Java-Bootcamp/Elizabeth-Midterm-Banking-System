package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.AddressControllerInterface;
import com.ironhack.banking_system.service.interfaces.AddressServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class AddressController implements AddressControllerInterface {

    @Autowired
    private AddressServiceInterface addressService;


}
