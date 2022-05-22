package com.ironhack.banking_system.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleToUserDTO {
    @NotNull
    private String username;
    @NotNull
    private String roleName;


}
