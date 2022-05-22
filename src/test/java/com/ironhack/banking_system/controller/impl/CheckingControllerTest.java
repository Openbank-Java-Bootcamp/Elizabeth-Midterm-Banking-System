//package com.ironhack.banking_system.controller.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ironhack.banking_system.model.AccountHolder;
//import com.ironhack.banking_system.model.Address;
//import com.ironhack.banking_system.model.Checking;
//import com.ironhack.banking_system.model.Money;
//import com.ironhack.banking_system.repository.AccountHolderRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//class CheckingControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private AccountHolderRepository accountHolderRepository;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
//
//
//    private AccountHolder accountHolder1;
//    private AccountHolder accountHolder2;
//
//
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(
////                List.of(
////                        new AccountHolder(
////                                "Marjorie Stewart-Baxter",
////                                "MJB1972",
////                                "catlady7",
////                                LocalDate.of(1972,4,1),
////                                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
////                        ),
////                        new AccountHolder(
////                                "Reginald Dawes",
////                                "ReggieD",
////                                "DrRegger",
////                                LocalDate.of(1998,12,31),
////                                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
////                        )
////                )
////        );
//
//        accountHolder1 = new AccountHolder(
//                "Marjorie Stewart-Baxter",
//                "MJB1972",
//                "catlady7",
//                LocalDate.of(1972,4,1),
//                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
//        );
//        accountHolder2 = new AccountHolder(
//                "Reginald Dawes",
//                "ReggieD",
//                "DrRegger",
//                LocalDate.of(1998,12,31),
//                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
//        );
//
//        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
//
//        List<Checking> CheckingAccounts = checkingRepository.saveAll(
//                List.of(
//                        new Checking(new Money(BigDecimal.valueOf(3000)), "secretKey3", accountHolder1,accountHolder2)
//                )
//        );
//    }
//
//    @AfterEach
//    void tearDown() {
//        checkingRepository.deleteAll();
//        accountHolderRepository.deleteAll();
//    }
//
//
//
//
//    @Test
//    void saveChecking() throws Exception {
//        CheckingDTO newCheckingAccount = new CheckingDTO(1L, 2L, new Money(BigDecimal.valueOf(1200)),"secretKey1");
//        String body = objectMapper.writeValueAsString(newCheckingAccount);
//        mockMvc.perform(post("/bank/accounts/checking").content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
//    }
//
//    @Test
//    void getCheckingBalance() throws Exception {
//        MvcResult result = mockMvc.perform(get("/bank/accounts/checking/balance/1"))
//                .andExpect(status().isOk()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("3000"));
//    }
//}