package com.dbc.vemserback.ecommerce.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class CreateUserDTO{
	@NotEmpty
    private String fullName;
	@NotNull
	@Email
    private String email;
	@NotNull
	@ValidPassword
    private String password;
	@Builder.Default
	private Optional<MultipartFile> file = Optional.empty();
}
