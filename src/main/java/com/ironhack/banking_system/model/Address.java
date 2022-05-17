package com.ironhack.banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {


    @NotEmpty
    @Column(name = "street_address")
    private String streetAddress;

    @NotEmpty
    @Column(name = "postal_code")
    private String postalCode; //string or long???

    @NotEmpty
    private String city;

    @NotEmpty
    @Column
    private String country;

}
