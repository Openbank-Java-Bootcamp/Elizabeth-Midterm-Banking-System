package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    private Money funds;


    public Transfer(Account originAccount, Account destinationAccount, Money funds) {
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.funds = funds;
    }
}
