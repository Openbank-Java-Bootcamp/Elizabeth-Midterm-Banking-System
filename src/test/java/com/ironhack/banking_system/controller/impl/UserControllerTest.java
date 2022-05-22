package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.Address;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AdminRepository;
import com.ironhack.banking_system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;



    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        List<Admin> Admins = adminRepository.saveAll(
                List.of(
                        new Admin("Clarence Thomas", "CThomas", "password1"),
                        new Admin("Sadie Hawkins", "SHawkins", "password2")
                )
        );
        List<AccountHolder> AccountHolders = accountHolderRepository.saveAll(
                List.of(
                        new AccountHolder(
                                "Marjorie Stewart-Baxter",
                                "MJB1972",
                                "catlady7",
                                LocalDate.of(1972, 04, 01),
                                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
                        ),
                        new AccountHolder(
                                "Reginald Dawes",
                                "ReggieD",
                                "DrRegger",
                                LocalDate.of(1998, 12, 31),
                                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
                        )

                )
        );
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
    }

    @Test
    void getUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/users"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Clarence Thomas"));
        assertTrue(result.getResponse().getContentAsString().contains("Sadie Hawkins"));
        assertTrue(result.getResponse().getContentAsString().contains("Marjorie Stewart-Baxter"));
        assertTrue(result.getResponse().getContentAsString().contains("Reginald Dawes"));
    }
}