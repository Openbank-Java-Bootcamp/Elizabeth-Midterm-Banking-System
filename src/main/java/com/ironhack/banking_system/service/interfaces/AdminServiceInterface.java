package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.Admin;

import java.util.List;

public interface AdminServiceInterface {

    Admin saveAdmin(Admin admin);

    List<Admin> getAdmins();
}
