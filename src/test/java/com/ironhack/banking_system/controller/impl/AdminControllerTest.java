package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        List<Admin> Admins = userRepository.saveAll(
                List.of(
                        new Admin("Clarence Thomas", "CThomas", "password1"),
                        new Admin("Sadie Hawkins", "SHawkins", "password2")
                )
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    void saveAdmin_ValidData_Created() throws Exception {
        String body = objectMapper.writeValueAsString(new Admin("Jerry Jones", "JJones", "password1"));
        mockMvc.perform(post("/bank/admins").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }


    @Test
    void saveAdmin_DuplicateUsername_Throws() throws Exception {
        String body = objectMapper.writeValueAsString(new Admin("Cindy Thomas", "CThomas", "password5"));
        mockMvc.perform(post("/bank/admins").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void getAdmins() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/admins"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Clarence Thomas"));
        assertTrue(result.getResponse().getContentAsString().contains("Sadie Hawkins"));
    }

    @Test
    void deleteAdmin_ValidID_Deleted() throws Exception {
        MvcResult result = mockMvc.perform(delete("/bank/admins/1"))
                .andExpect(status().isNoContent()).andReturn();
        assertTrue(adminRepository.findById(1L).isEmpty());
    }

    @Test
    void deleteAdmin_InvalidID_Throws() throws Exception {
        MvcResult result = mockMvc.perform(delete("/bank/admins/10"))
                .andExpect(status().isBadRequest()).andReturn();
    }

}