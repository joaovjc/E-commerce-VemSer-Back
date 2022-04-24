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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserAdmDto;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

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
    public UserLoginDto signUp(@Valid @ModelAttribute(name = "data") CreateUserDTO createDTO, @RequestPart(name = "file",required = false) MultipartFile file, BindingResult bindingResult) throws BusinessRuleException {
    	if(bindingResult.hasErrors()) {
    		StringBuilder builder = new StringBuilder();
        	bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
    		throw new BusinessRuleException(builder.toString());
    	}
    	UserAdmDto userAdmDto = objectMapper.convertValue(createDTO,UserAdmDto.class);
        userAdmDto.setGroups(Groups.USER);
    	UserLoginDto createUser = userService.createUser(userAdmDto, file);
    	
    	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                		createUser.getUsername(),
                		createDTO.getPassword()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate,createUser);
        return token;
    }
}