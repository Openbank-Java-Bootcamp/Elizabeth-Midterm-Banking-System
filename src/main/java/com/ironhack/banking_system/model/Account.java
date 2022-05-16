package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import static java.util.Currency.*;


@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @Column(name = "penalty_fee")
    private final Money penaltyFee = new Money(new BigDecimal("40"), getInstance("USD"));


    //constructor for two AccountOwners
    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public Account(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }
}
