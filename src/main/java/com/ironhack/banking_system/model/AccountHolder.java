package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name ="account_holder")
@NoArgsConstructor
public class AccountHolder extends User{

    @NotNull
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull //necessary?
    @Embedded
    private Address primaryAddress;

    @Embedded
    private Address mailingAddress;


    public AccountHolder(Long id, String name, String username, String password, Date dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(id, name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(Long id, String name, String username, String password, Date dateOfBirth, Address primaryAddress) {
        super(id, name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }
}
