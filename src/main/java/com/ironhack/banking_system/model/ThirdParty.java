package com.ironhack.banking_system.model;

import com.ironhack.banking_system.DTO.TransferThirdPartyDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "third_party")
@NoArgsConstructor
public class ThirdParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "hashed_key")
    private String hashedKey;


    public ThirdParty(String name, String hashedKey) {
        this.name = name;
        this.hashedKey = hashedKey;
    }
}
