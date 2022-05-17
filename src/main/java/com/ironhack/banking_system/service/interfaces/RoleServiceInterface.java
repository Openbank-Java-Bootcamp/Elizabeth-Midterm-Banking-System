package com.ironhack.banking_system.service.interfaces;

import com.ironhack.banking_system.model.Role;

public interface RoleServiceInterface {
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
}
