package com.dbc.vemserback.ecommerce.controller.others;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.user.UserCreateDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserPageDTO;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface Admin {
	@ApiOperation(value = "Recives any user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user created"),
			@ApiResponse(code = 403, message = "you dont have the permission to create an user"),
			@ApiResponse(code = 500, message = "One exception was throwed") 
	})
	public void admCreateUser(@Valid @ModelAttribute(name = "data") UserCreateDTO userCreateDTO, @RequestPart(name = "file",required = false) MultipartFile file, BindingResult bindingResult) throws BusinessRuleException;
	
	@ApiOperation(value = "Recives the new Group and the User Id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "user group changed"),
			@ApiResponse(code = 403, message = "you dont have the permission to change the user"),
			@ApiResponse(code = 500, message = "One exception was throwed") 
	})
	public void admSetGroupUser(@RequestParam Groups groups, @RequestParam Integer idUser) throws BusinessRuleException;
	
//	@ApiOperation(value = "Recives the new Group and the User Id")
//	@ApiResponses(value = { 
//			@ApiResponse(code = 200, message = "user group changed"),
//			@ApiResponse(code = 403, message = "you dont have the permission to create an user"),
//			@ApiResponse(code = 500, message = "One exception was throwed") 
//	})
//	public Page<UserPageDTO> admGetAllUsers(@RequestParam int page);
//
//	@ApiOperation(value = "Recives the full name or a part of it of the User")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "return the user wich contain the given name"),
//			@ApiResponse(code = 403, message = "you dont have the permission to create an user"),
//			@ApiResponse(code = 500, message = "One exception was throwed")
//	})
//	public List<UserPageDTO> admGetAllUsersByFullName(@RequestParam String nome);
}