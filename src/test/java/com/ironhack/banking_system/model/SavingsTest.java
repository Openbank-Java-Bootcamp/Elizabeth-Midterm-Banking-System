package com.ironhack.banking_system.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SavingsTest {

    private AccountHolder accountHolder1;
    private Savings savings1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                new Name("Marjorie", "Stewart-Baxter"),
                "MJB1972",
                "catlady7",
                new Date(1972, 04,01),
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );

        savings1 = new Savings(accountHolder1, "secretKey1");
        //customMinBalanceSavings = new Savings(accountHolder1, "secretKey2", new Money(new BigDecimal("99")));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setMinimumBalance_BelowLimit_100() {
        savings1.setMinimumBalance(new Money(new BigDecimal(99)));
        assertEquals(new BigDecimal("100.00"), savings1.getMinimumBalance().getAmount());
    }

    @Test
    void setMinimumBalance_AboveLimit_1000() {
        savings1.setMinimumBalance(new Money(new BigDecimal(1001)));
        assertEquals(new BigDecimal("1000.00"), savings1.getMinimumBalance().getAmount());
    }

    @Test
    void setMinimumBalance_WithinLimit_Works() {
        savings1.setMinimumBalance(new Money(new BigDecimal(500)));
        assertEquals(new BigDecimal("500.00"), savings1.getMinimumBalance().getAmount());
    }

    @Test
    void setInterestRate_BelowLimit_0() {
        savings1.setInterestRate(new BigDecimal(-1));
        assertEquals(new BigDecimal(0), savings1.getInterestRate());
    }

    @Test
    void setInterestRate_AboveLimit_50percent() {
        savings1.setInterestRate(new BigDecimal(.6));
        assertEquals(new BigDecimal(.5), savings1.getInterestRate());
    }

    @Test
    void setInterestRate_WithinLimit_Works() {
        savings1.setInterestRate(new BigDecimal(.35));
        assertEquals(new BigDecimal(.35), savings1.getInterestRate());
    }
}