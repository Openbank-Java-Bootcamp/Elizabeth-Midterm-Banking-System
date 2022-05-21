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
public class ThirdPartyTransfer extends Transfer{


    @ManyToOne
    @JoinColumn(name = "third_party")
    private ThirdParty thirdParty;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;



    public ThirdPartyTransfer(Money funds, ThirdParty thirdParty, Account account) {
        super(funds);
        this.thirdParty = thirdParty;
        this.account = account;
    }
}
