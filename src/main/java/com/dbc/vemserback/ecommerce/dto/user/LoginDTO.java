package com.dbc.vemserback.ecommerce.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

	@ApiModelProperty(value = "email", example = "teste@mail.com", required = true)
	@NotNull
	private String username;

	@ApiModelProperty(value = "password", example = "String@123", required = true)
	@NotEmpty
	private String password;

}
