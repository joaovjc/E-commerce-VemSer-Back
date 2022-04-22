package com.dbc.vemserback.ecommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
	
	@NotNull
	private String email;
	@NotEmpty
	private String password;
	
}
