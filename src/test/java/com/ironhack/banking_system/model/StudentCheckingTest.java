package com.ironhack.banking_system.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentCheckingTest {

    private AccountHolder accountHolder1;

    private StudentChecking studentChecking1;

    @BeforeEach
    void setUp() {
        accountHolder1 = new AccountHolder(
                "Dennis Menace",
                "DennisM",
                "rascal42",
                LocalDate.of(2000,8,20),
                new Address("86 Windy Lane", "London", "UK", "83745")
        );

        studentChecking1 = new StudentChecking(new Money(BigDecimal.valueOf(200)), "secretkey3", accountHolder1);
    }

    @Test
    void debitAccount_SufficientFunds_AccountDebited() {
        BigDecimal debitAmount = BigDecimal.valueOf(20);
        BigDecimal expectedBalanceAmount = studentChecking1.getBalance().getAmount().subtract(debitAmount);
        studentChecking1.debitAccount(new Money(debitAmount));
        assertEquals(expectedBalanceAmount, studentChecking1.getBalance().getAmount());
    }

    @Test
    void creditAccount_InsufficientFunds_Throws() {
        BigDecimal debitAmount = BigDecimal.valueOf(205);
        assertThrows(IllegalArgumentException.class, ()->studentChecking1.debitAccount(new Money(debitAmount)));
    }
}