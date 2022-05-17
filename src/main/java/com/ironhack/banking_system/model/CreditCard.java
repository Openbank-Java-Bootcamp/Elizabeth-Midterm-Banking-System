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
    public CreditCard(AccountHolder primaryOwner) {
        super(primaryOwner);
    }

    //constructor for default creditLimit and custom interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, BigDecimal interestRate) {
        super(primaryOwner);
        this.interestRate = interestRate;
    }

    //constructor for custom creditLimit and default interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money creditLimit) {
        super(primaryOwner);
        this.creditLimit = creditLimit;
    }

    //constructor for custom creditLimit and custom interestRate, 1 account holder
    public CreditCard(AccountHolder primaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    //constructor for default creditLimit and default interestRate, 2 account holders
//    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
//        super(primaryOwner, secondaryOwner);
//    }

    //constructor for default creditLimit and custom interestRate, 2 account holders
//    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
//        super(primaryOwner, secondaryOwner);
//        this.interestRate = interestRate;
//    }

    //constructor for custom creditLimit and default interestRate, 2 account holders
//    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit) {
//        super(primaryOwner, secondaryOwner);
//        this.creditLimit = creditLimit;
//    }

    //constructor for custom creditLimit and interestRate, 2 account holders
//    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit, BigDecimal interestRate) {
//        super(primaryOwner, secondaryOwner);
//        this.creditLimit = creditLimit;
//        this.interestRate = interestRate;
//    }


}
