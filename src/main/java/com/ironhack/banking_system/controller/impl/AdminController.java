package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.AdminControllerInterface;
import com.ironhack.banking_system.service.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class AdminController implements AdminControllerInterface {

    @Autowired
    private AdminServiceInterface adminService;


}
