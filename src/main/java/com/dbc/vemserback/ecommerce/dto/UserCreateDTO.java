package com.dbc.vemserback.ecommerce.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.dbc.vemserback.ecommerce.validation.ValidPassword;

import lombok.Data;

@Data
public class UserCreateDTO {

	@NotEmpty
    private String fullName;
	@Email
    private String email;
	@ValidPassword
    private String password;
	private String profilePic;
}
