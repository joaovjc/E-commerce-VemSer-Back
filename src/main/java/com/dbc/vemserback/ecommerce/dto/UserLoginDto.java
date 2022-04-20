package com.dbc.vemserback.ecommerce.dto;

import java.util.List;

import com.dbc.vemserback.ecommerce.entity.RuleEntity;

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
//	private String profileImage;
	private List<String> rules;
	private String token;
	
}
