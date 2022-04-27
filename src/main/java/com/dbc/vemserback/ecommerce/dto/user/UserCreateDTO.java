package com.dbc.vemserback.ecommerce.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.dbc.vemserback.ecommerce.enums.Groups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDTO {
        @NotEmpty
        private String fullName;
        @NotNull
        @Email
        private String email;
        @NotNull
    	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must contain at least one digit,"
    			+ "Password must contain at least one lowercase Latin character,"
    			+ "Password must contain at least one uppercase Latin character,"
    			+ "Password must contain at least one special character like ! @ # & ( ),"
    			+ "Password must contain a length of at least 8 characters and a maximum of 20 characters")
        private String password;
        @NotNull
        private Groups groups;
}
