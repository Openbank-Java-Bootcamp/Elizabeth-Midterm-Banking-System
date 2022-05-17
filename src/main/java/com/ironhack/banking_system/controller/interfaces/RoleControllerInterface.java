package com.ironhack.banking_system.controller.interfaces;

import com.ironhack.banking_system.DTO.RoleToUserDTO;
import com.ironhack.banking_system.model.Role;

public interface RoleControllerInterface {
    void saveRole(Role role);
    void addRoleToUser(RoleToUserDTO roleToUserDTO);
}
