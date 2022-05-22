package com.ironhack.banking_system;


import com.ironhack.banking_system.DTO.AccountDTO;
import com.ironhack.banking_system.enums.AccountType;
import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.service.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(RoleService roleService, AdminService adminService, AccountHolderService accountHolderService, AccountService accountService) {
		return args -> {
			roleService.saveRole(new Role("ROLE_ADMIN"));
			roleService.saveRole(new Role("ROLE_USER"));

			adminService.saveAdmin(new Admin("Clarence Thomas", "CThomas", "password1"));


			adminService.saveAdmin(new Admin("Sadie Hawkins", "SHawkins", "password2"));


			accountHolderService.saveAccountHolder(
					new AccountHolder(
							"Marjorie Stewart-Baxter",
							"MJB1972",
							"catlady7",
							LocalDate.of(1972, 04, 02),
							new Address("c/ Alameda 46", "Madrid", "Spain", "28012")
							));
			accountHolderService.saveAccountHolder(
					new AccountHolder(
							"Reginald Dawes",
							"ReggieD",
							"DrRegger",
							LocalDate.of(1998, 12,31),
							new Address("c/ Atocha 216", "Toledo", "Spain", "26784")
					));
			accountHolderService.saveAccountHolder(
					new AccountHolder(
							"Carrie Winston",
							"CWinston",
							"CarrieBearie",
							LocalDate.of(2002,2,27),
							new Address("567 Hampshire Lane", "Edina", "United States", "55315")
					));
			accountHolderService.saveAccountHolder(
					new AccountHolder(
							"Greg Winston",
							"GWinston",
							"greg123",
							LocalDate.of(1975,10,15),
							new Address("567 Hampshire Lane", "Edina", "United States", "55315")
					));

			roleService.addRoleToUser("CThomas", "ROLE_ADMIN");
			roleService.addRoleToUser("SHawkins", "ROLE_ADMIN");
			roleService.addRoleToUser("MJB1972", "ROLE_USER");
			roleService.addRoleToUser("ReggieD", "ROLE_USER");
			roleService.addRoleToUser("CWinston", "ROLE_USER");
			roleService.addRoleToUser("GWinston", "ROLE_USER");
//
//
//			accountService.saveAccount(
//					new AccountDTO(
//							AccountType.savings,
//							new Money(BigDecimal.valueOf(2000)),
//							"secretKey4",
//							3L
//					)
//			);
//
//			checkingService.saveChecking(
//					new CheckingDTO(
//							5L,
//							6L,
//							new Money(BigDecimal.valueOf(450)),
//							"secretKey5")
//					);
		};
	}

}
