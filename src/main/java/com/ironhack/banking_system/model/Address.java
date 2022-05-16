package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "street_address")
    private String streetAddress;

    @NotEmpty
    @Column(name = "postal_code")
    private long postalCode; //string or long???

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

   // @ManyToOne// how to map with two columns in AccountHolder???
    private AccountHolder accountHolder;
}
