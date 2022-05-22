package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.DTO.RoleToUserDTO;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.model.Role;
import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.repository.RoleRepository;
import com.ironhack.banking_system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class RoleControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));

        adminRepository.save(new Admin("Amy Adams", "AAdams", "password0"));

    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }



    @Test
    void saveRole() throws Exception {
        String body = objectMapper.writeValueAsString(new Role("AUDITOR"));
        mockMvc.perform(post("/bank/roles").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void addRoleToUser() throws Exception {
        String body = objectMapper.writeValueAsString(new RoleToUserDTO("AAdams", "AUDITOR"));
        mockMvc.perform(post("/bank/roles/addtouser").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

    }
}