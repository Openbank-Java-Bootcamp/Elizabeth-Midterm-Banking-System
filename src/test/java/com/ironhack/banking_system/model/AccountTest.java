package com.ironhack.banking_system.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private AccountHolder accountHolder3;
    private AccountHolder accountHolder4;

    private Checking checking1;
    private StudentChecking studentChecking1;
    private Savings savings1;
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
        accountHolder2 = new AccountHolder(
                "Reginald Dawes",
                "ReggieD",
                "DrRegger",
                LocalDate.of(1998, 12,31),
                new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
        );
        accountHolder3 = new AccountHolder(
                "Carrie Winston",
                "CWinston",
                "CarrieBearie",
                LocalDate.of(2002,2,27),
                new Address("567 Hampshire Lane", "Edina", "United States", "55315")
        );
        accountHolder4 = new AccountHolder(
                "Greg Winston",
                "GWinston",
                "greg123",
                LocalDate.of(1975,10,15),
                new Address("567 Hampshire Lane", "Edina", "United States", "55315")
        );



        checking1 = new Checking(new Money(BigDecimal.valueOf(255)), "secretKey1", accountHolder1);
        studentChecking1 = new StudentChecking(new Money(BigDecimal.valueOf(10)), "secretKey2", accountHolder3);
        savings1 = new Savings(new Money(BigDecimal.valueOf(1050)), "secretKey3", accountHolder2, null, null);
        creditCard1 = new CreditCard(new Money(BigDecimal.valueOf(0)), "secretKey4", accountHolder4, null, null);
    }


    @Test//checking and savings have minimumBalance
    void applyPenaltyFeeIfApplicable_AboveMinBalance_NoFeeApplied() {
        BigDecimal originalBalanceAmount = checking1.getBalance().getAmount();
        checking1.applyPenaltyFeeIfApplicable();
        assertEquals(originalBalanceAmount, checking1.getBalance().getAmount());

        originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.applyPenaltyFeeIfApplicable();
        assertEquals(originalBalanceAmount, savings1.getBalance().getAmount());
    }

    @Test
    void applyPenaltyFeeIfApplicable_BelowMinBalance_FeeApplied() {
        //set checking balance below default 250
        checking1.setBalance(new Money(new BigDecimal("200")));
        checking1.applyPenaltyFeeIfApplicable();
        assertEquals(new BigDecimal("160.00"), checking1.getBalance().getAmount());

        //set savings balance below default 1000
        savings1.setBalance(new Money(BigDecimal.valueOf(999)));
        savings1.applyPenaltyFeeIfApplicable();
        assertEquals(new BigDecimal("959.00"), savings1.getBalance().getAmount());
    }


    @Test //credit card and savings have interestRate
    void applyInterestIfApplicable_NotDue_InterestNotApplied() {
        BigDecimal originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.applyInterestIfApplicable();
        assertEquals(originalBalanceAmount, savings1.getBalance().getAmount());

        originalBalanceAmount = creditCard1.getBalance().getAmount();
        creditCard1.applyInterestIfApplicable();
        assertEquals(originalBalanceAmount, creditCard1.getBalance().getAmount());

    }

    @Test
    void applyInterestIfApplicable_Due_InterestApplied() {
        BigDecimal originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.setDateInterestDue(LocalDate.now().minusDays(1));
        savings1.applyInterestIfApplicable();
        BigDecimal balanceWithInterest = originalBalanceAmount.add(originalBalanceAmount.multiply(savings1.getInterestRate())).setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(balanceWithInterest, savings1.getBalance().getAmount());

        originalBalanceAmount = creditCard1.getBalance().getAmount();
        creditCard1.setDateInterestDue(LocalDate.now().minusDays(1));
        creditCard1.applyInterestIfApplicable();
        balanceWithInterest = originalBalanceAmount.add(originalBalanceAmount.multiply(creditCard1.getInterestRate())).setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(balanceWithInterest, creditCard1.getBalance().getAmount());
    }



    @Test// checking, student checking, savings
    void debitAccount_SufficientFunds_DebitApplied() {
        Money debitAmount = new Money(new BigDecimal("4.00"));

        BigDecimal originalBalanceAmount = checking1.getBalance().getAmount();
        checking1.debitAccount(debitAmount);
        assertEquals(originalBalanceAmount.subtract(debitAmount.getAmount()), checking1.getBalance().getAmount());

        originalBalanceAmount = studentChecking1.getBalance().getAmount();
        studentChecking1.debitAccount(debitAmount);
        assertEquals(originalBalanceAmount.subtract(debitAmount.getAmount()), studentChecking1.getBalance().getAmount());

        originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.debitAccount(debitAmount);
        assertEquals(originalBalanceAmount.subtract(debitAmount.getAmount()), savings1.getBalance().getAmount());

        originalBalanceAmount = creditCard1.getBalance().getAmount();
        creditCard1.debitAccount(debitAmount);
        assertEquals(originalBalanceAmount.add(debitAmount.getAmount()), creditCard1.getBalance().getAmount());
    }

    @Test
    void debitAccount_InsufficientFunds_Throws(){
        Money debitAmount = new Money(new BigDecimal("1500.00"));

        assertThrows(IllegalArgumentException.class, ()-> checking1.debitAccount(debitAmount));
        assertThrows(IllegalArgumentException.class, ()-> studentChecking1.debitAccount(debitAmount));
        assertThrows(IllegalArgumentException.class, ()-> savings1.debitAccount(debitAmount));
        assertThrows(IllegalArgumentException.class, ()-> creditCard1.debitAccount(debitAmount));
    }

    @Test
    void debitAccount_FeesApplied() {
        Money debitAmount = new Money(new BigDecimal("60.00"));
        BigDecimal penaltyFee = checking1.getPenaltyFee().getAmount(); //all penalty fees 40

        //default minimumBalance is 250 for checking
        BigDecimal originalBalanceAmount = checking1.getBalance().getAmount();
        BigDecimal balanceMinusDebitAndFee = originalBalanceAmount.subtract(debitAmount.getAmount().add(penaltyFee));
        checking1.debitAccount(debitAmount);
        assertEquals(balanceMinusDebitAndFee, checking1.getBalance().getAmount());

        //default minimumBalance is 1000 for savings
        originalBalanceAmount = savings1.getBalance().getAmount();
        balanceMinusDebitAndFee = originalBalanceAmount.subtract(debitAmount.getAmount().add(penaltyFee));
        savings1.debitAccount(debitAmount);
        assertEquals(balanceMinusDebitAndFee, savings1.getBalance().getAmount());
    }



    @Test// checking, student checking, savings
    void creditAccount_CreditApplied() {
        Money creditAmount = new Money(new BigDecimal("1500.00"));

        BigDecimal originalBalanceAmount = checking1.getBalance().getAmount();
        checking1.creditAccount(creditAmount);
        assertEquals(originalBalanceAmount.add(creditAmount.getAmount()), checking1.getBalance().getAmount());

        originalBalanceAmount = studentChecking1.getBalance().getAmount();
        studentChecking1.creditAccount(creditAmount);
        assertEquals(originalBalanceAmount.add(creditAmount.getAmount()), studentChecking1.getBalance().getAmount());

        originalBalanceAmount = savings1.getBalance().getAmount();
        savings1.creditAccount(creditAmount);
        assertEquals(originalBalanceAmount.add(creditAmount.getAmount()), savings1.getBalance().getAmount());

        originalBalanceAmount = creditCard1.getBalance().getAmount();
        creditCard1.creditAccount(creditAmount);
        assertEquals(originalBalanceAmount.subtract(creditAmount.getAmount()), creditCard1.getBalance().getAmount());
    }
}