package com.springbank.user.core.usercore.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Size(min = 5, max = 25, message = "Username must be from 5 to 25 characters")
    private String username;
    @Size(min = 7, max = 64,message = "Password must be from 5 to 25 characters")
    private String password;
    @NotNull(message = "Specify at least one user role")
    @Valid
    private List<Role> roles;
}
