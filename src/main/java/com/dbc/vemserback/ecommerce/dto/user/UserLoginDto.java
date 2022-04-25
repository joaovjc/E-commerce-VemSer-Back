package com.dbc.vemserback.ecommerce.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginDto {

	private String username;
	private String profileImage;
	private String fullName;
	private String profile;
	private String token;

}
