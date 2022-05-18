package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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


}
