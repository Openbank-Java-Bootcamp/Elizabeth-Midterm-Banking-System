package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.model.Admin;

import java.util.List;

public interface AdminControllerInterface {

    List<Admin> getAdmins();

    void saveAdmin(Admin admin);
}
