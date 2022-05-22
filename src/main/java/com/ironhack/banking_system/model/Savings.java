package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import static java.util.Currency.*;

@Data
@NoArgsConstructor
@Entity
public class Savings extends Account{


    //CONSTRUCTORS
    public Savings(Money balance, String secretKey, AccountHolder primaryOwner, Money minimumBalance, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner);
        super.setDateInterestDue(super.getCreationDate().plusYears(1));
        this.setMinimumBalance(minimumBalance);
        this.setInterestRate(interestRate);
    }

    public Savings(Money balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money minimumBalance, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner);
        super.setDateInterestDue(super.getCreationDate().plusYears(1));
        this.setMinimumBalance(minimumBalance);
        this.setInterestRate(interestRate);
    }


    //CUSTOM SETTERS
    @Override
    public void setMinimumBalance(Money minimumBalance) {
        Money defaultMinimum = new Money(BigDecimal.valueOf(1000));
        if (minimumBalance == null) {
            super.setMinimumBalance(defaultMinimum);
        } else {
            BigDecimal lowerLimit = new BigDecimal("100");
            BigDecimal upperLimit = new BigDecimal("1000");
            BigDecimal minimumBalanceAmount = minimumBalance.getAmount();

            if (minimumBalanceAmount.compareTo(lowerLimit) <= 0) {
                super.setMinimumBalance(new Money(lowerLimit));
            } else if (minimumBalanceAmount.compareTo(upperLimit) >= 1) {
                super.setMinimumBalance(new Money(upperLimit));
            } else {
                super.setMinimumBalance(minimumBalance);
            }
        }
    }

    @Override
    public void setInterestRate(BigDecimal interestRate) {
        BigDecimal defaultRate = new BigDecimal("0.0025");
        if (interestRate == null) {
            super.setInterestRate(defaultRate);
        } else {
            BigDecimal lowerLimit = new BigDecimal("0.0");
            BigDecimal upperLimit = new BigDecimal("0.5");

            if (interestRate.compareTo(lowerLimit) <= 0) {
                super.setInterestRate(lowerLimit);
            } else if (interestRate.compareTo(upperLimit) >= 1) {
                super.setInterestRate(upperLimit);
            } else {
                super.setInterestRate(interestRate);
            }
        }
    }



}
