package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import static java.util.Currency.*;

@Entity
@Data
@NoArgsConstructor
public class Checking extends Account{

    @NotEmpty
    @Column(name = "secret_key")
    private String secretKey;

    @Embedded
    //@Column(name = "minimum_balance") //final as this does not change
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))
    })
    private final Money minimumBalance = new Money(new BigDecimal("250"), getInstance("USD"));

    @Embedded
    //@Column(name = "monthly_maintenance_fee") //final as this does not change
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))
    })
    private final Money monthlyMaintenanceFee = new Money(new BigDecimal("12"), getInstance("USD"));

    @Column(name = "creation_date")
    private final Date creationDate = new Date(); // will automatically assign today's date

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    //constructor for 1 owner


    public Checking(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
    }

    //constructor for 2 owners
    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        this.secretKey = secretKey;
        this.status = AccountStatus.ACTIVE;
    }
}
