package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static java.util.Currency.*;


@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "account_type")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount"))
    })
    private Money balance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))
    })
    private final Money penaltyFee = new Money(new BigDecimal("40"));

    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now(); // will automatically assign today's date


    //constructor for two AccountOwners
    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.balance = balance;
    }

    public Account(AccountHolder primaryOwner, Money balance) {
        this.primaryOwner = primaryOwner;
        this.balance = balance;
    }

    public void debitAccount(Money funds) {
        BigDecimal debitAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        if (currentBalanceAmount.compareTo(debitAmount) <= 0) {
            throw new IllegalArgumentException("Amount exceeds balance.");
        } else {
            BigDecimal newBalanceAmount = this.getBalance().getAmount().subtract(debitAmount);
            this.setBalance(new Money(newBalanceAmount));
        }
    }

    public void creditAccount(Money funds) {
        BigDecimal creditAmount = funds.getAmount();
        BigDecimal currentBalanceAmount = this.getBalance().getAmount();
        BigDecimal newBalanceAmount = this.getBalance().getAmount().add(creditAmount);
        this.setBalance(new Money(newBalanceAmount));
    }
}
