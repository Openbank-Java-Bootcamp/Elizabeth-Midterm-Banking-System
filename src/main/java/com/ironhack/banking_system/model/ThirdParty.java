package com.ironhack.banking_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "third_party")
public class ThirdParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "hashed_key")
    private String hashedKey;
}
