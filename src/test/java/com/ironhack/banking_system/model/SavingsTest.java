package com.ironhack.banking_system.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SavingsTest {

    private AccountHolder accountHolder1;
    private Savings savings1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                "Marjorie Stewart-Baxter",
                "MJB1972",
                "catlady7",
                LocalDate.of(1972,4,1),
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );

        savings1 = new Savings(new Money(new BigDecimal("1200.00")),"secretKey1", accountHolder1, null, null, null);


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
    void setMinimumBalance_Null_Default() {
        savings1.setMinimumBalance(null);
        assertEquals(new BigDecimal("1000.00"), savings1.getMinimumBalance().getAmount());
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

    @Test
    void setInterestRate_Null_Default() {
        savings1.setInterestRate(null);
        assertEquals(new BigDecimal("0.0025"), savings1.getInterestRate());
    }

    @Test
    void applyPenaltyFeeIfApplicable_BelowMinBalance_FeeApplied() {
        savings1.setBalance(new Money(new BigDecimal("900")));
        savings1.applyPenaltyFeeIfApplicable();
        assertEquals(new BigDecimal("860.00"), savings1.getBalance().getAmount());
    }

    @Test
    void applyPenaltyFeeIfApplicable_AboveMinBalance_FeeNotApplied() {
        savings1.setBalance(new Money(new BigDecimal("1005")));
        savings1.applyPenaltyFeeIfApplicable();
        assertEquals(new BigDecimal("1005.00"), savings1.getBalance().getAmount());
    }

    @Test
    void applyInterestIfApplicable_DueForInterest_InterestApplied() {
        savings1.setDateInterestDue(LocalDate.now().minusDays(1));
        BigDecimal originalBalanceAmount = savings1.getBalance().getAmount();
        BigDecimal expectedAmount = originalBalanceAmount.add(originalBalanceAmount.multiply(savings1.getInterestRate()));
        savings1.applyInterestIfApplicable();

        assertEquals(expectedAmount.setScale(2), savings1.getBalance().getAmount());
    }

    @Test
    void applyInterestIfApplicable_NotDueForInterest_BalanceUnchanged() {
        BigDecimal originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.applyInterestIfApplicable();

        assertEquals(originalBalanceAmount, savings1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFunds_AccountDebited() {
        BigDecimal expectedBalanceAmount = savings1.getBalance().getAmount().subtract(BigDecimal.valueOf(20));
        savings1.debitAccount(new Money(BigDecimal.valueOf(20)));
        assertEquals(expectedBalanceAmount, savings1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFundsDropsBelowMinBalance_AccountDebitedAndPenaltyFeeApplied() {
        BigDecimal expectedBalanceAmount = savings1.getBalance().getAmount().subtract(BigDecimal.valueOf(205+40)); //subtracting debit amount and penalty fee amount
        savings1.debitAccount(new Money(BigDecimal.valueOf(205)));
        assertEquals(expectedBalanceAmount, savings1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFundsAlreadyBelowMinBalance_AccountDebitedButNoPenaltyFeeApplied() {
        savings1.setBalance(new Money(BigDecimal.valueOf(800))); //setting balance below default 1000 minimumBalance
        BigDecimal expectedBalanceAmount = savings1.getBalance().getAmount().subtract(BigDecimal.valueOf(20)); //expecting only debit amount to be taken
        savings1.debitAccount(new Money(BigDecimal.valueOf(20)));
        assertEquals(expectedBalanceAmount, savings1.getBalance().getAmount());
    }

    @Test
    void debitAccount_InsufficientFunds_Throws() {
        BigDecimal debitAmount = BigDecimal.valueOf(1205);
        assertThrows(IllegalArgumentException.class, ()->savings1.debitAccount(new Money(debitAmount)));
    }


}