package com.ironhack.banking_system.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Admin extends User{

    public Admin(String name, String username, String password) {
        super(name, username, password);
    }
}
