package com.dbc.vemserback.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserCreateDTO;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.FileService;
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
    private final FileService fileService;
	
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
    public UserLoginDto signUp(@RequestBody @Valid UserCreateDTO userCreateDTO, @RequestParam(name = "file", required = false) MultipartFile multipartFile) throws BusinessRuleException {
    	String upload = null;
    	if(multipartFile!=null)upload = fileService.upload(multipartFile);
    	
    	UserCreateDTO createUser = userService.createUser(userCreateDTO, upload);
    	
    	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                		createUser.getEmail(),
                		userCreateDTO.getPassword()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate);
        return token;
    }

}