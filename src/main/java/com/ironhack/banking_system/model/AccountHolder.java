package com.ironhack.banking_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
//@Table(name ="account_holder")
public class AccountHolder extends User{

   // @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull //necessary?
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "primary_street_address")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_postal_code")),
            @AttributeOverride(name = "city", column = @Column(name = "primary_city")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_country"))
    })
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetAddress", column = @Column(name = "mailing_street_address")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country"))
    })
    private Address mailingAddress;

    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accounts;


    public AccountHolder(String name, String username, String password, int birthYear, int birthMonth, int birthDate, Address primaryAddress, Address mailingAddress) {
        super(name, username, password);
        setDateOfBirth(birthYear, birthMonth, birthDate);
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String name, String username, String password, int birthYear, int birthMonth, int birthDate, Address primaryAddress) {
        super(name, username, password);
        setDateOfBirth(birthYear, birthMonth, birthDate);
        this.primaryAddress = primaryAddress;
    }

    //custom setter for dateOfBirth


    public void setDateOfBirth(int birthYear, int birthMonth, int birthDate) {
        LocalDate dobAsLocalDate = LocalDate.of(birthYear, birthMonth, birthDate);
        this.dateOfBirth = dobAsLocalDate;
    }

    //method to calculate age
    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        if ( dateOfBirth == null || currentDate == null) {
            throw new NullPointerException();
        } else {
            return Period.between(dateOfBirth, currentDate).getYears();
        }

    }
}
