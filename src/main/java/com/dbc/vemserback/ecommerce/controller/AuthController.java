package com.dbc.vemserback.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
	
    @PostMapping("/login")
    public UserLoginDto auth(@RequestBody @Valid LoginDTO loginDTO) throws BusinessRuleException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                		loginDTO.getEmail(),
                		loginDTO.getPassword()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate);
        return token;
    }
    
    @PostMapping("/sign-up")
    public UserLoginDto signUp(@Valid @RequestPart(name = "data") CreateUserDTO CreateDTO) throws BusinessRuleException {
//    public UserLoginDto signUp(@Valid @ModelAttribute(name = "data") CreateUserDTO CreateDTO, BindingResult bindingResult) throws BusinessRuleException {
//    	if(bindingResult.hasErrors()) {
//    		StringBuilder builder = new StringBuilder();
//        	bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
//    		throw new BusinessRuleException(builder.toString());
//    	}
    	
    	UserLoginDto createUser = userService.createUser(CreateDTO);
    	
    	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                		createUser.getUsername(),
                		CreateDTO.getPassword()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate,createUser);
        return token;
    }

}