//package com.ironhack.banking_system.controller.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ironhack.banking_system.model.AccountHolder;
//import com.ironhack.banking_system.model.Address;
//import com.ironhack.banking_system.model.Money;
//import com.ironhack.banking_system.repository.AccountHolderRepository;
//import com.ironhack.banking_system.repository.SavingsRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@SpringBootTest
//class SavingsControllerTest {
//
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private SavingsRepository savingsRepository;
//
//    @Autowired
//    private AccountHolderRepository accountHolderRepository;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private AccountHolder accountHolder1;
//    private AccountHolder accountHolder2;
//
//
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////        accountHolder1 = new AccountHolder(
////                "Marjorie Stewart-Baxter",
////                "MJB1972",
////                "catlady7",
////                LocalDate.of(1972,4,1),
////                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
////        );
////        accountHolder2 = new AccountHolder(
////                "Reginald Dawes",
////                "ReggieD",
////                "DrRegger",
////                LocalDate.of(1998,12,31),
////                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
////        );
////        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2));
//
//        List<AccountHolder> accountHolders = accountHolderRepository.saveAll(
//                List.of(
//                        new AccountHolder(
//                                "Marjorie Stewart-Baxter",
//                                "MJB1972",
//                                "catlady7",
//                                LocalDate.of(1972,4,1),
//                                new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
//                        ),
//                        new AccountHolder(
//                                "Reginald Dawes",
//                                "ReggieD",
//                                "DrRegger",
//                                LocalDate.of(1998,12,31),
//                                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
//                        )
//                )
//        );
//
//
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        accountHolderRepository.deleteAll();
//    }
//
//
//
//
//
////    @Test
////    void saveSavings() throws Exception {
////        String body = objectMapper.writeValueAsString(accountHolder1, new Money(new BigDecimal("1200.00")),"secretKey1");
////        mockMvc.perform(post("/bank/accounts/savings").content(body)
////                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
////    }
//
//    @Test
//    void updateBalance() {
//    }
//
//    @Test
//    void updateStatus() {
//    }
//
//}