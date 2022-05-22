package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import static java.util.Currency.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCard extends Account{

    @Embedded
    //@Column(name = "credit_limit")
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount"))
    })
    private Money creditLimit = new Money(new BigDecimal("100"));



    //CONSTRUCTORS
    public CreditCard(Money balance, String secretKey, AccountHolder primaryOwner, BigDecimal interestRate, Money creditLimit) {
        super(balance, secretKey, primaryOwner);
        super.setDateInterestDue(super.getCreationDate().plusMonths(1));
        setInterestRate(interestRate);
        setCreditLimit(creditLimit);
    }

    public CreditCard(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, Money creditLimit) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        super.setDateInterestDue(super.getCreationDate().plusMonths(1));
        setInterestRate(interestRate);
        setCreditLimit(creditLimit);
    }


    //custom setters
    @Override
    public void setInterestRate(BigDecimal interestRate) {
        BigDecimal defaultRate = new BigDecimal("0.2");
        if (interestRate == null) {
            super.setInterestRate(defaultRate);
        } else {
            BigDecimal lowerLimit = new BigDecimal("0.1");
            BigDecimal upperLimit = new BigDecimal("0.2");

            if (interestRate.compareTo(lowerLimit) <= 0) {
                super.setInterestRate(lowerLimit);
            } else if (interestRate.compareTo(upperLimit) >= 1) {
                super.setInterestRate(upperLimit);
            } else {
                super.setInterestRate(interestRate);
            }
        }
    }

    public void setCreditLimit(Money creditLimit) {
        Money defaultLimit = new Money(new BigDecimal("100.00"));
        if (creditLimit == null) {
            this.creditLimit = defaultLimit;
        } else {
            BigDecimal lowerLimit = new BigDecimal("100.00");
            BigDecimal upperLimit = new BigDecimal("100000.00");
            BigDecimal creditLimitAmount = creditLimit.getAmount();

            if (creditLimitAmount.compareTo(lowerLimit) <= 0) {
                this.creditLimit = new Money(lowerLimit);
            } else if (creditLimitAmount.compareTo(upperLimit) >= 1) {
                this.creditLimit = new Money(upperLimit);
            } else {
                this.creditLimit = creditLimit;
            }
        }
    }


    //METHODS
    @Override
    public void applyInterestIfApplicable() {
        LocalDate currentDate = LocalDate.now();
        int daysInterestOverdue = Period.between(currentDate, super.getDateInterestDue()).getDays();
        //if interest hasn't been applied in over a year
        if (daysInterestOverdue >= 0) {
            BigDecimal balanceAmount = super.getBalance().getAmount(); //get current balance
            BigDecimal monthlyInterestRate = this.getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
            BigDecimal interestAmount = balanceAmount.multiply(monthlyInterestRate); //calculate interest to be added
            BigDecimal newBalanceAmount = balanceAmount.add(interestAmount); //calculate new balance
            super.setBalance(new Money(newBalanceAmount));
            super.setDateInterestDue(LocalDate.now().plusYears(1));
        }
    }

    @Override //need to override as balance on creditCard account is opposite of the other accounts
    public void debitAccount(Money funds) {
        BigDecimal debitAmount = funds.getAmount();
        //apply interest if applicable
        applyInterestIfApplicable();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = currentBalanceAmount.add(debitAmount);
        BigDecimal creditLimitAmount = this.getCreditLimit().getAmount();
        //check if debit will put balance above credit limit and deny if so
        if (newBalanceAmount.compareTo(creditLimitAmount) > 0) {
            throw new IllegalArgumentException("Amount exceeds credit limit.");
        } else {
            this.setBalance(new Money(newBalanceAmount));
        }
    }

    @Override //need to override as balance on creditCard account is opposite of the other accounts
    public void creditAccount(Money funds) {
        BigDecimal creditAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = currentBalanceAmount.subtract(creditAmount);
        this.setBalance(new Money(newBalanceAmount));
    }

}
