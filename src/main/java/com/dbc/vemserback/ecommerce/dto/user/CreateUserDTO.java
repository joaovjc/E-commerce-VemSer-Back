package com.dbc.vemserback.ecommerce.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dbc.vemserback.ecommerce.validation.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserDTO{
	@NotEmpty
    private String fullName;
	@NotNull
	@Email
    private String username;
	@NotNull
//	@ValidPassword
    private String password;
}
