package com.ironhack.banking_system;

import com.ironhack.banking_system.model.*;
import com.ironhack.banking_system.service.impl.AccountHolderService;
import com.ironhack.banking_system.service.impl.AdminService;
import com.ironhack.banking_system.service.impl.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	CommandLineRunner run(RoleService roleService, AdminService adminService, AccountHolderService accountHolderService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));
			roleService.saveRole(new Role(null, "ROLE_USER"));

			adminService.saveAdmin(new Admin(new Name("Clarence", "Thomas"), "CThomas", "password1"));
			adminService.saveAdmin(new Admin(new Name("Sadie", "Hawkins"), "SHawkins", "password2"));


			accountHolderService.saveAccountHolder(
					new AccountHolder(
							new Name("Marjorie", "Stewart-Baxter"),
							"MJB1972",
							"catlady7",
							//new Date(1972, 04,01),
							LocalDate.of(1972, 04, 01),
							new Address("c/ Alameda 46", "28012", "Madrid", "Spain")
							));
			accountHolderService.saveAccountHolder(
					new AccountHolder(
							new Name("Reginald", "Dawes"),
							"ReggieD",
							"DrRegger",
							//new Date(1998, 12,31),
							LocalDate.of(1998, 12, 31),
							new Address("c/ Atocha 216", "28039", "Madrid", "Spain")
					));

			roleService.addRoleToUser("CThomas", "ROLE_ADMIN");
			roleService.addRoleToUser("SHawkins", "ROLE_ADMIN");
			roleService.addRoleToUser("MJB1972", "ROLE_USER");
			roleService.addRoleToUser("ReggieD", "ROLE_USER");

		};
	}

}
