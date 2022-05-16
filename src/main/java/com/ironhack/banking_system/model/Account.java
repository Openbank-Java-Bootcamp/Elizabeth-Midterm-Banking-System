package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import static java.util.Currency.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor //change to constructor without id???
@Table(name = "account")
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private User primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private User secondaryOwner;

    @Column(name = "penalty_fee")
    private Money penaltyFee = new Money(new BigDecimal("40"), getInstance("USD"));

}
