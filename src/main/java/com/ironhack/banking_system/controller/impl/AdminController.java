package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.AdminControllerInterface;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.service.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class AdminController implements AdminControllerInterface {

    @Autowired
    private AdminServiceInterface adminService;


    @GetMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAdmin(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
    }
}
