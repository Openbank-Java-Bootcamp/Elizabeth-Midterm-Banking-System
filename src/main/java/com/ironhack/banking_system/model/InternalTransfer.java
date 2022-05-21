package com.ironhack.banking_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class InternalTransfer extends Transfer{

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;



    public InternalTransfer(Money funds, Account originAccount, Account destinationAccount) {
        super(funds);
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
    }
}
