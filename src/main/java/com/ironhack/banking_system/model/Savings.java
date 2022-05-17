package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import static java.util.Currency.*;

@Data
@NoArgsConstructor
@Entity
public class Savings extends Account{

    @NotEmpty
    @Column(name = "secret_key")
    private String secretKey;

    //@DecimalMin(value = "100") //will this work???
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private Money minimumBalance = new Money(new BigDecimal("1000"), getInstance("USD"));

    @Column(name = "creation_date")
    private final Date creationDate = new Date(); // will automatically assign today's date

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @DecimalMax(value = ".05")
    @DecimalMin(value = "0")
    @Column(name = "interest_rate")
    private BigDecimal interestRate = new BigDecimal("0.0025");



    //constructor for default minimumBalance and default interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, String secretKey) {
        super(primaryOwner);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
    }

    //constructor for default minimumBalance and default interestRate, 2 account holders
//    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
//        super(primaryOwner, secondaryOwner);
//        this.secretKey = secretKey;
//        this.status = AccountStatus.ACTIVE;
//    }

    //constructor with default minimumBalance and custom interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, String secretKey, BigDecimal interestRate) {
        super(primaryOwner);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
        this.status = AccountStatus.ACTIVE;
    }

    //constructor with default minimumBalance and custom interestRate, 2 account holders
//    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal interestRate) {
//        super(primaryOwner, secondaryOwner);
//        this.secretKey = secretKey;
//        this.interestRate = interestRate;
//        this.status = AccountStatus.ACTIVE;
//    }

    //constructor with custom minimumBalance and default interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, String secretKey, Money minimumBalance) {
        super(primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.status = AccountStatus.ACTIVE;
    }

    //constructor with custom minimumBalance and default interestRate, 2 account holders
//    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Money minimumBalance) {
//        super(primaryOwner, secondaryOwner);
//        this.secretKey = secretKey;
//        this.minimumBalance = minimumBalance;
//        this.status = AccountStatus.ACTIVE;
//    }

    //constructor with custom minimumBalance and interestRate, 1 account holder
    public Savings(AccountHolder primaryOwner, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.status = AccountStatus.ACTIVE;
    }


    //constructor with custom minimumBalance and interestRate, 2 account holders
//    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Money minimumBalance, BigDecimal interestRate) {
//        super(primaryOwner, secondaryOwner);
//        this.secretKey = secretKey;
//        this.minimumBalance = minimumBalance;
//        this.interestRate = interestRate;
//        this.status = AccountStatus.ACTIVE;
//    }
}
