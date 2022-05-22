package com.ironhack.banking_system.controller.impl;

import com.ironhack.banking_system.DTO.RoleToUserDTO;
import com.ironhack.banking_system.model.Role;
import com.ironhack.banking_system.service.interfaces.RoleServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/roles")
public class RoleController {

    @Autowired
    private RoleServiceInterface roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @PostMapping("/addtouser")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@RequestBody @Valid RoleToUserDTO roleToUserDTO) {
        roleService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
    }
}
