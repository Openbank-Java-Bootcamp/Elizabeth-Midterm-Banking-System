package com.ironhack.banking_system.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    private AccountHolder accountHolder1;
    private CreditCard creditCard1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                "Marjorie Stewart-Baxter",
                "MJB1972",
                "catlady7",
                LocalDate.of(1972,4,1),
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );

        creditCard1 = new CreditCard(new Money(new BigDecimal("0")), "secretKey6", accountHolder1, null, null);
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
    void setInterestRate_Null_Default() {
        creditCard1.setInterestRate(null);
        assertEquals(new BigDecimal("0.2"), creditCard1.getInterestRate());
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

    @Test
    void setCreditLimit_Null_Default() {
        creditCard1.setCreditLimit(null);
        assertEquals(new BigDecimal("100.00"), creditCard1.getCreditLimit().getAmount());
    }


    @Test
    void applyInterestIfApplicable_DueForInterest_InterestApplied() {
        creditCard1.setDateInterestDue(LocalDate.now().minusDays(1));
        BigDecimal originalBalanceAmount = creditCard1.getBalance().getAmount();
        BigDecimal monthlyInterestRate = creditCard1.getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
        BigDecimal expectedAmount = originalBalanceAmount.add(originalBalanceAmount.multiply(monthlyInterestRate));

        creditCard1.applyInterestIfApplicable();
        assertEquals(expectedAmount.setScale(2), creditCard1.getBalance().getAmount());
    }

    @Test
    void applyInterestIfApplicable_NotDueForInterest_NoInterestApplied() {
        BigDecimal originalBalanceAmount = creditCard1.getBalance().getAmount();
        creditCard1.applyInterestIfApplicable();
        assertEquals(originalBalanceAmount, creditCard1.getBalance().getAmount());
    }

    @Test
    void debitAccount_InterestDue_InterestAndDebitApplied() {
        creditCard1.setDateInterestDue(LocalDate.now().minusDays(1));
        creditCard1.applyInterestIfApplicable();
        BigDecimal originalBalanceAmount = creditCard1.getBalance().getAmount();
        BigDecimal monthlyInterestRate = creditCard1.getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
        Money debitFunds = new Money(new BigDecimal("10.00"));
        BigDecimal expectedAmount = originalBalanceAmount.add(originalBalanceAmount.add(debitFunds.getAmount()));

        creditCard1.debitAccount(debitFunds);
        assertEquals(expectedAmount, creditCard1.getBalance().getAmount());

    }

    @Test
    void creditAccount_InterestDue_InterestAndDebitApplied() {
        creditCard1.setDateInterestDue(LocalDate.now().minusDays(1));
        creditCard1.applyInterestIfApplicable();
        BigDecimal originalBalanceAmount = creditCard1.getBalance().getAmount();
        BigDecimal monthlyInterestRate = creditCard1.getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
        Money creditFunds = new Money(new BigDecimal("10.00"));
        BigDecimal expectedAmount = originalBalanceAmount.subtract(originalBalanceAmount.add(creditFunds.getAmount()));

        creditCard1.creditAccount(creditFunds);
        assertEquals(expectedAmount, creditCard1.getBalance().getAmount());

    }

}