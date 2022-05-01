package com.dbc.vemserback.ecommerce.controller.login;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.user.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.user.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserLoginDto;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface LoginAPI {

	@ApiOperation(value = "Recives any user email and password")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "returns the token alongside with the image and the user roles"),
			@ApiResponse(code = 403, message = "user or login are wrong"),
			@ApiResponse(code = 500, message = "One exception was throwed") 
	})
	public UserLoginDto auth(@RequestBody @Valid LoginDTO loginDTO) throws BusinessRuleException;
	
	@ApiOperation(value = "Recives an user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "returns the token along side with the image and the user roles"),
			@ApiResponse(code = 403, message = "user or login are wrong"),
			@ApiResponse(code = 500, message = "One exception was throwed") 
	})
	public UserLoginDto signUp(@Valid @ModelAttribute(name = "data") CreateUserDTO createDTO, BindingResult bindingResult, @RequestPart(name = "file",required = false) MultipartFile file)
			throws BusinessRuleException;

}
