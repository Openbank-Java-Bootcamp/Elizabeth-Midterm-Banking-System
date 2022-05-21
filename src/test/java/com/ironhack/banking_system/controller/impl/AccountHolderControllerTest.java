package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.ironhack.banking_system.model.AccountHolder;
import com.ironhack.banking_system.model.Address;
import com.ironhack.banking_system.model.Admin;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();




    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
        accountHolderRepository.deleteAll();
    }




    @Test
    void getAccountHolderById_ValidId_Works() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/accountholders/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Marjorie"));
    }

    @Test
    void getAccountHolderById_InvalidId_BadRequest() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/accountholders/10"))
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void getAccountHolders_ReturnsAccountHolders() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/accountholders"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Marjorie"));
        assertTrue(result.getResponse().getContentAsString().contains("Reginald"));
    }

    @Test
    void saveAccountHolder_ValidData_Created() throws Exception {
        String body = objectMapper.writeValueAsString(new AccountHolder("Marjorie Stewart-Baxter",
                "MJB1972",
                "catlady7",
                LocalDate.parse("1972-04-01"),
                new Address("Alameda 46", "Madrid", "Spain", "28012")));
        mockMvc.perform(post("/bank/accountholders").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}