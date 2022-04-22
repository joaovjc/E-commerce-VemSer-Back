package com.dbc.vemserback.ecommerce.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateDto extends UserCreateDTO{
	
	private MultipartFile file;
	
}
