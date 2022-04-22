package com.dbc.vemserback.ecommerce.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.validation.ValidPassword;

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
	@Email
    private String email;
	@ValidPassword
    private String password;
	
	private MultipartFile profileImage;
}
