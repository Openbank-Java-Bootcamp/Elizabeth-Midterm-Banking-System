package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.Address;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        accountHolder1 = new AccountHolder(
                "Marjorie Stewart-Baxter",
                "MJB1972",
                "catlady7",
                //new Date(1972, 04,01),
                1972, 04, 01,
                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
        );
        accountHolder2 = new AccountHolder(
                "Reginald Dawes",
                "ReggieD",
                "DrRegger",
                //new Date(1998, 12,31),
                1998, 12, 31,
                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
        );
        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
    }

    @Test
    void getAccountHolderById() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/accounts/savings/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Marjorie"));
    }

    @Test
    void getAccountHolders() {
    }

    @Test
    void saveAccountHolder() {
    }
}