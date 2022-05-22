package com.ironhack.banking_system.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.banking_system.DTO.TransferDTO;
import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.repository.AccountHolderRepository;
import com.ironhack.banking_system.repository.AccountRepository;
import com.ironhack.banking_system.repository.ThirdPartyRepository;
import com.ironhack.banking_system.repository.TransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransferControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;
    private AccountHolder accountHolder4;
    private AccountHolder accountHolder5;
    private AccountHolder accountHolder6;
    private AccountHolder accountHolder7;
    private AccountHolder accountHolder8;
    private Checking checkingAccount;
    private Checking checking2;
    private StudentChecking studentCheckingAccount;
    private Savings savingsAccount;
    private CreditCard creditCardAccount;




    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //make some account holders
        accountHolder1 = new AccountHolder("Marjorie Stewart-Baxter", "MJB1972", "catlady7",
                LocalDate.parse("1972-04-02"),
                new Address("c/ Alameda 46", "Madrid", "Spain", "28012"));
        accountHolder2 = new AccountHolder("Reginald Dawes", "ReggieD", "DrRegger",
                LocalDate.parse("1998-12-31"),
                new Address("c/ Atocha 216", "Toledo", "Spain", "26784"));
        accountHolder3 = new AccountHolder("Carrie Winston", "CWinston", "CarrieBearie",
                LocalDate.of(2002,2,27),
                new Address("567 Hampshire Lane", "Edina", "United States", "55315"));
        accountHolder4 = new AccountHolder("Greg Winston", "GWinston", "greg123",
                LocalDate.of(1975,10,15),
                new Address("567 Hampshire Lane", "Edina", "United States", "55315"));
        accountHolder5 = new AccountHolder("Maria Gomez", "MGomez", "password5",
                LocalDate.of(1980, 3,2),
                new Address("Gran Via 834", "Madrid", "Spain", "27341"));
        accountHolder6 = new AccountHolder("Mario Lopez", "MLopez", "password6",
                LocalDate.of(1978, 9, 14),
                new Address("Gran Via 834", "Madrid", "Spain", "27341"));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4,
                accountHolder5, accountHolder6));

        //make some accounts
        checkingAccount = new Checking(new Money(BigDecimal.valueOf(300)), "secretKey1", accountHolder1, accountHolder2);
        studentCheckingAccount= new StudentChecking(new Money(BigDecimal.valueOf(100)), "secretKey2", accountHolder3, accountHolder4);
        savingsAccount= new Savings(new Money(BigDecimal.valueOf(1500)), "secretKey3", accountHolder5, accountHolder6, null, null);
        creditCardAccount= new CreditCard(new Money(BigDecimal.valueOf(75)), "secretKey4", accountHolder5, accountHolder6, null, null);
        checking2 = new Checking(new Money(BigDecimal.valueOf(300)), "secretKey1", accountHolder1);

        accountRepository.saveAll(List.of(checkingAccount, studentCheckingAccount, savingsAccount, creditCardAccount));

        //make a third party
        thirdPartyRepository.save(new ThirdParty("Other Bank", "hashedKey1"));
    }

    @AfterEach
    void tearDown() {
        transferRepository.deleteAll();
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "MJB1972", password = "catlady7", roles="USER")
    void saveTransfer_ValidData_Created() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        Money originalOriginAccountBalance = checkingAccount.getBalance();
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        String body = objectMapper.writeValueAsString(new TransferDTO(1L, funds, "Greg Winston", 2L));
        //transfer object is saved
        mockMvc.perform(post("/bank/transfers").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        //receiving account balance increased by fund amount
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount().add(funds.getAmount()), currentDestinationAccountBalance.getAmount());
        //sending account balance decreased by fund amount
        Money currentOriginAccountBalance = accountRepository.findById(1L).get().getBalance();
        assertEquals(originalOriginAccountBalance.getAmount().subtract(funds.getAmount()), currentOriginAccountBalance.getAmount());
    }



    @Test
    @WithMockUser(username = "MLopez", password = "password6", roles="USER")
    void saveTransfer_ToCreditCard_Created() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        Money originalOriginAccountBalance = savingsAccount.getBalance();
        Money originalDestinationAccountBalance = creditCardAccount.getBalance();
        String body = objectMapper.writeValueAsString(new TransferDTO(3L, funds,
                "Maria Gomez", 4L));
        //transfer object is saved
        mockMvc.perform(post("/bank/transfers").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        //receiving CreditCard account balance decreased by fund amount
        Money currentDestinationAccountBalance = accountRepository.findById(4L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount().subtract(funds.getAmount()), currentDestinationAccountBalance.getAmount());
        //sending account balance decreased by fund amount
        Money currentOriginAccountBalance = accountRepository.findById(3L).get().getBalance();
        assertEquals(originalOriginAccountBalance.getAmount().subtract(funds.getAmount()), currentOriginAccountBalance.getAmount());
    }

    @Test
    @WithMockUser(username = "MJB1972", password = "catlady7", roles="USER")
    void saveTransfer_WrongName_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        Money originalOriginAccountBalance = checkingAccount.getBalance();
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        String body = objectMapper.writeValueAsString(new TransferDTO(1L, funds,
                "Harry Potter", 2L));
        //transfer object is saved
        mockMvc.perform(post("/bank/transfers").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "MJB1972", password = "catlady7", roles="USER")
    void saveTransfer_WrongSendingAccount_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        Money originalOriginAccountBalance = checkingAccount.getBalance();
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();

        String body = objectMapper.writeValueAsString(new TransferDTO(4L, funds,
                "Harry Potter", 2L));
        mockMvc.perform(post("/bank/transfers").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    void saveTransferFromThirdParty_ValidData_Created() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using account 2L
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "secretKey2"));
        mockMvc.perform(post("/bank/fromthirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        //funds credited to account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount().add(funds.getAmount()), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferFromThirdParty_InvalidHashedKey_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using account 2L
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //throws bad request
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "secretKey2"));
        mockMvc.perform(post("/bank/fromthirdparty/hashedKey2").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds added to account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferFromThirdParty_InvalidSecretKey_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using account 2L
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //throws bad request
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "wrongSecretKey"));
        mockMvc.perform(post("/bank/fromthirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds added to account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferFromThirdParty_InvalidAccount_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using account 2L
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //throws bad request
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 15L, "secretKey2"));
        mockMvc.perform(post("/bank/fromthirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds added to account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferToThirdParty_ValidData_Created() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using account 2L
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "secretKey2"));
        mockMvc.perform(post("/bank/tothirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        //funds debited from account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount().subtract(funds.getAmount()), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferToThirdParty_InsufficientFunds_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(150));
        //using StudentChecking account with balance of only 100
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "secretKey2"));
        mockMvc.perform(post("/bank/tothirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds debited from account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferToThirdParty_InvalidHashedKey_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using StudentChecking account with balance of only 100
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "secretKey2"));
        mockMvc.perform(post("/bank/tothirdparty/hashedKey2").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds debited from account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferToThirdParty_InvalidSecretKey_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using StudentChecking account with balance of only 100
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 2L, "wrongSecretKey"));
        mockMvc.perform(post("/bank/tothirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds debited from account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

    @Test
    void saveTransferToThirdParty_InvalidAccount_Throws() throws Exception {
        Money funds = new Money(BigDecimal.valueOf(25));
        //using StudentChecking account with balance of only 100
        Money originalDestinationAccountBalance = studentCheckingAccount.getBalance();
        //transfer created
        String body = objectMapper.writeValueAsString(new TransferThirdPartyDTO(funds, 15L, "secretKey2"));
        mockMvc.perform(post("/bank/tothirdparty/hashedKey1").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        //no funds debited from account
        Money currentDestinationAccountBalance = accountRepository.findById(2L).get().getBalance();
        assertEquals(originalDestinationAccountBalance.getAmount(), currentDestinationAccountBalance.getAmount());
    }

}