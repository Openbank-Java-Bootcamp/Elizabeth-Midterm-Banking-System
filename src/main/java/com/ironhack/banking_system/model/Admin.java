package com.ironhack.banking_system.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Admin extends User{

    public Admin(Name name, String username, String password) {
        super(name, username, password);
    }
}
