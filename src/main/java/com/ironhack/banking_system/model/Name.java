package com.ironhack.banking_system.model;

import com.ironhack.banking_system.enums.Salutation;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class Name {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String middleName;
    @Enumerated(EnumType.STRING)
    private Salutation salutation;



    //constructor w/o salutation
    public Name(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.salutation = Salutation.None;
    }

    //constructor w/o middleName or salutation
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salutation = Salutation.None;
    }

    //constructor w/o middleName but w/ salutation
    public Name(String firstName, String lastName, Salutation salutation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salutation = salutation;
    }

}
