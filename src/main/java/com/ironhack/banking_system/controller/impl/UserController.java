package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.controller.interfaces.UserControllerInterface;
import com.ironhack.banking_system.model.User;
import com.ironhack.banking_system.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/users")
public class UserController implements UserControllerInterface {

    @Autowired
    private UserServiceInterface userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
