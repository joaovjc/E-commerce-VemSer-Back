package com.dbc.vemserback.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
	
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	
}
