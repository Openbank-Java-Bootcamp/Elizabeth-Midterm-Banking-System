package com.ironhack.banking_system.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckingTest {

    private AccountHolder accountHolder1;
    private Checking checking1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                new Name("Marjorie", "Stewart-Baxter"),
                "MJB1972",
                "catlady7",
                //new Date(1972, 04,01),
                1972, 04,01,
                new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
        );

        checking1 = new Checking(accountHolder1, new Money(BigDecimal.valueOf(500)), "secretkey1");
    }

    @Test
    void applyPenaltyFeeIfApplicable_BelowMinBalance_FeeApplied() {
        checking1.setBalance(new Money(new BigDecimal("200")));
        checking1.applyPenaltyFeeIfApplicable();
        assertEquals(new BigDecimal("160.00"), checking1.getBalance().getAmount());
    }

    @Test
    void applyPenaltyFeeIfApplicable_AboveMinBalance_NoFeeApplied() {
        BigDecimal originalBalanceAmount = checking1.getBalance().getAmount();
        checking1.applyPenaltyFeeIfApplicable();
        assertEquals(originalBalanceAmount, checking1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFunds_AccountDebited() {
        BigDecimal expectedBalanceAmount = checking1.getBalance().getAmount().subtract(BigDecimal.valueOf(20));
        checking1.debitAccount(new Money(BigDecimal.valueOf(20)));
        assertEquals(expectedBalanceAmount, checking1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFundsDropsBelowMinBalance_AccountDebitedAndPenaltyFeeApplied() {
        BigDecimal expectedBalanceAmount = checking1.getBalance().getAmount().subtract(BigDecimal.valueOf(255+40)); //subtracting debit amount and penalty fee amount
        checking1.debitAccount(new Money(BigDecimal.valueOf(255)));
        assertEquals(expectedBalanceAmount, checking1.getBalance().getAmount());
    }

    @Test
    void debitAccount_SufficientFundsAlreadyBelowMinBalance_AccountDebitedButNoPenaltyFeeApplied() {
        checking1.setBalance(new Money(BigDecimal.valueOf(200))); //setting balance below default 1000 minimumBalance
        BigDecimal expectedBalanceAmount = checking1.getBalance().getAmount().subtract(BigDecimal.valueOf(20)); //expecting only debit amount to be taken
        checking1.debitAccount(new Money(BigDecimal.valueOf(20)));
        assertEquals(expectedBalanceAmount, checking1.getBalance().getAmount());
    }

    @Test
    void debitAccount_InsufficientFunds_Throws() {
        BigDecimal debitAmount = BigDecimal.valueOf(505);
        assertThrows(IllegalArgumentException.class, ()->checking1.debitAccount(new Money(debitAmount)));
    }
}