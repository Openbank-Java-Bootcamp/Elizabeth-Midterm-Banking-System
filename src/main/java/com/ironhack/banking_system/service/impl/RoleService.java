package com.ironhack.banking_system.service.impl;

import com.ironhack.banking_system.model.Role;
import com.ironhack.banking_system.model.User;
import com.ironhack.banking_system.repository.RoleRepository;
import com.ironhack.banking_system.repository.UserRepository;
import com.ironhack.banking_system.service.interfaces.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        //find user by username
        User user = userRepository.findByUsername(username);
        //find role
        Role role = roleRepository.findByName(roleName);
        //find user's list of roles and save new role to it
        user.getRoles().add(role);
        //save user back to db
        userRepository.save(user);
    }
}
