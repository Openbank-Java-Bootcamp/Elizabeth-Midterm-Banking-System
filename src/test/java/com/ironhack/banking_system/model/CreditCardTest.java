package com.ironhack.banking_system.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    private AccountHolder accountHolder1;
    private CreditCard creditCard1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                new Name("Marjorie", "Stewart-Baxter"),
                "MJB1972",
                "catlady7",
                //new Date(1972, 04,01),
                LocalDate.of(1972, 04,01),
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );

        creditCard1 = new CreditCard(accountHolder1, new Money(new BigDecimal("0")));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setInterestRate_BelowLimit_10percent() {
        creditCard1.setInterestRate(new BigDecimal(".05"));
        assertEquals(new BigDecimal(".1"), creditCard1.getInterestRate());
    }

    @Test
    void setInterestRate_AboveLimit_20percent() {
        creditCard1.setInterestRate(new BigDecimal(".25"));
        assertEquals(new BigDecimal(".2"), creditCard1.getInterestRate());
    }

    @Test
    void setInterestRate_WithinLimit_Works() {
        creditCard1.setInterestRate(new BigDecimal(".15"));
        assertEquals(new BigDecimal(".15"), creditCard1.getInterestRate());
    }


    @Test
    void setCreditLimit_BelowLimit_100() {
        creditCard1.setCreditLimit(new Money(new BigDecimal("10")));
        assertEquals(new BigDecimal("100.00"), creditCard1.getCreditLimit().getAmount());
    }

    @Test
    void setCreditLimit_AboveLimit_100() {
        creditCard1.setCreditLimit(new Money(new BigDecimal("200000")));
        assertEquals(new BigDecimal("100000.00"), creditCard1.getCreditLimit().getAmount());
    }

    @Test
    void setCreditLimit_WithinLimit_Works() {
        creditCard1.setCreditLimit(new Money(new BigDecimal("5000")));
        assertEquals(new BigDecimal("5000.00"), creditCard1.getCreditLimit().getAmount());
    }

}