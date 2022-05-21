package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.DTO.AccountBalanceOnlyDTO;
import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AccountRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

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
                        ),
                        new AccountHolder(
							"Carrie Winston",
							"CWinston",
							"CarrieBearie",
							LocalDate.of(2002,2,27),
							new Address("567 Hampshire Lane", "Edina", "United States", "55315")
					    ),
                        new AccountHolder(
							"Greg Winston",
							"GWinston",
							"greg123",
							LocalDate.of(1975,10,15),
							new Address("567 Hampshire Lane", "Edina", "United States", "55315")
					)

                )
        );
        List<Account> Accounts = accountRepository.saveAll(
                List.of(
                        new Checking(new Money(new BigDecimal("500")), "secretKey1", accountHolderRepository.findById(4L).get(),null),
                        new Savings(new Money(new BigDecimal("300")), "secretKey2", accountHolderRepository.findById(2L).get(), null, null, null)
                )
        );
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void saveAccount_CheckingAccounts_Created() throws Exception {
        //1 owner
        AccountDTO accountDTO = new AccountDTO(AccountType.checking, new Money(BigDecimal.valueOf(500)), "secretKey1", 1L);
        String body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //2 owners
        accountDTO = new AccountDTO(AccountType.checking, new Money(BigDecimal.valueOf(500)), "secretKey2", 1L, 2L);
        body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void saveAccount_StudentCheckingAccounts_Created() throws Exception {
        //1 owner
        AccountDTO accountDTO = new AccountDTO(AccountType.checking, new Money(BigDecimal.valueOf(500)),
                "secretKey1", 3L);
        String body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //2 owners
        accountDTO = new AccountDTO(AccountType.checking, new Money(BigDecimal.valueOf(500)),
                "secretKey2", 3L, 4L);
        body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void saveAccount_SavingsAccounts_Created() throws Exception {
        //1 owner
        AccountDTO accountDTO = new AccountDTO(AccountType.savings, new Money(BigDecimal.valueOf(500)),
                "secretKey1", 3L);
        String body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //2 owners, custom minimumBalance and interestRate
        accountDTO = new AccountDTO(AccountType.savings, new Money(BigDecimal.valueOf(500)),
                "secretKey2", 3L, 4L, new Money(BigDecimal.valueOf(300)), new BigDecimal(".2"));
        body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void saveAccount_CreditCardAccounts_Created() throws Exception {
        //1 owner
        AccountDTO accountDTO = new AccountDTO(AccountType.creditcard, new Money(BigDecimal.valueOf(0)),
                "secretKey1", 3L);
        String body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        //2 owners, custom interestRate and interestRate
        accountDTO = new AccountDTO(AccountType.creditcard, new Money(BigDecimal.valueOf(0)),
                "secretKey2", 3L, 4L, new BigDecimal("0.15"), new Money(BigDecimal.valueOf(250)));
        body = objectMapper.writeValueAsString(accountDTO);
        mockMvc.perform(post("/bank/accounts").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }


    @Test
    void getAccount() throws Exception {
        MvcResult result = mockMvc.perform(get("/bank/accounts/1"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Greg Winston"));

    }

    @Test
    void updateAccountBalance() throws Exception {
        String body = objectMapper.writeValueAsString(new AccountBalanceOnlyDTO(new Money(new BigDecimal("800"))));
        mockMvc.perform(patch("/bank/accounts/balance/1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        assertEquals(new Money(new BigDecimal("800")),accountRepository.findById(1L).get().getBalance());
    }
}