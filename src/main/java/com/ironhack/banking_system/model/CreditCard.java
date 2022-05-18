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
    private Money creditLimit = new Money(new BigDecimal("100"), getInstance("USD"));

    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal(".2");

    private LocalDate lastDateInterestApplied = LocalDate.now();//will set it to date of creation


    //constructor for default creditLimit and default interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money balance) {
        super(primaryOwner, balance);
    }

    //constructor for default creditLimit and custom interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money balance, BigDecimal interestRate) {
        super(primaryOwner, balance);
        this.interestRate = interestRate;
    }

    //constructor for custom creditLimit and default interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money balance, Money creditLimit) {
        super(primaryOwner, balance);
        this.creditLimit = creditLimit;
    }

    //constructor for custom creditLimit and custom interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money balance, Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner, balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    //constructor for default creditLimit and default interestRate, 2 account holders
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        super(primaryOwner, secondaryOwner, balance);
    }

    //constructor for default creditLimit and custom interestRate, 2 account holders
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        this.interestRate = interestRate;
    }

    //constructor for custom creditLimit and default interestRate, 2 account holders
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money creditLimit) {
        super(primaryOwner, secondaryOwner, balance);
        this.creditLimit = creditLimit;
    }

    //constructor for custom creditLimit and interestRate, 2 account holders
    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }


    //setter for interestRate and creditLimit
    public void setInterestRate(BigDecimal interestRate) {
        BigDecimal lowerLimit = new BigDecimal("0.1");
        BigDecimal upperLimit = new BigDecimal("0.2");

        if (interestRate.compareTo(lowerLimit) <= 0) {
            this.interestRate = lowerLimit;
        } else if (interestRate.compareTo(upperLimit) >= 1) {
            this.interestRate = upperLimit;
        } else {
            this.interestRate = interestRate;
        }
    }

    public void setCreditLimit(Money creditLimit) {
        BigDecimal lowerLimit = new BigDecimal("100");
        BigDecimal upperLimit = new BigDecimal("100000");
        BigDecimal creditLimitAmount = creditLimit.getAmount();

        if (creditLimitAmount.compareTo(lowerLimit) <= 0) {
            this.creditLimit = new Money(lowerLimit);
        } else if (creditLimitAmount.compareTo(upperLimit) >= 1) {
            this.creditLimit = new Money(upperLimit);
        } else {
            this.creditLimit = creditLimit;
        }
    }


    //methods
    public void applyInterestIfApplicable() {
        LocalDate currentDate = LocalDate.now();
        int monthsSinceLastInterestApplied = Period.between(this.getLastDateInterestApplied(), currentDate).getMonths();
        //if interest hasn't been applied in over a year
        if (monthsSinceLastInterestApplied >= 1) {
            BigDecimal balanceAmount = super.getBalance().getAmount(); //get current balance
            BigDecimal monthlyInterestRate = this.getInterestRate().divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
            BigDecimal interestAmount = balanceAmount.multiply(monthlyInterestRate); //calculate interest to be added
            BigDecimal newBalanceAmount = balanceAmount.add(interestAmount); //calculate new balance
            super.setBalance(new Money(newBalanceAmount));
            lastDateInterestApplied = LocalDate.now();
        }
    }

    @Override //need to override as balance on creditCard account is opposite other accounts
    public void debitAccount(Money funds) {
        BigDecimal debitAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = currentBalanceAmount.add(debitAmount);
        BigDecimal creditLimitAmount = this.getCreditLimit().getAmount();
        if (newBalanceAmount.compareTo(creditLimitAmount) > 0) {
            throw new IllegalArgumentException("Amount exceeds credit limit.");
        } else {
            this.setBalance(new Money(newBalanceAmount));
        }
    }

    @Override //need to override as balance on creditCard account is opposite other accounts
    public void creditAccount(Money funds) {
        BigDecimal creditAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = this.getBalance().getAmount().subtract(creditAmount);
        this.setBalance(new Money(newBalanceAmount));
    }

}
