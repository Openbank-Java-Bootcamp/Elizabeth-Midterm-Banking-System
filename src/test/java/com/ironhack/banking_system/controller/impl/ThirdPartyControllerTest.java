package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.model.Money;
import com.ironhack.banking_system.model.ThirdParty;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.CheckingRepository;
import com.ironhack.banking_system.repository.ThirdPartyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        List<ThirdParty> ThirdParties = thirdPartyRepository.saveAll(
                List.of(
                        new ThirdParty("Other Bank", "hashedKey1"),
                        new ThirdParty("Some Store", "hashedKey2")
                )
        );

    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    void saveThirdParty() throws Exception {
        String body = objectMapper.writeValueAsString(new ThirdParty("Your Roommate", "hashedKey3"));
        mockMvc.perform(post("/bank/thirdparties").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }


}