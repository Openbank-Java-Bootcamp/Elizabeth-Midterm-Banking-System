package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.User;

import java.util.List;

public interface UserServiceInterface {

    User saveUser(User user);
    List<User> getUsers();

}
