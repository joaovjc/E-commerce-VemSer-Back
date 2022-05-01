package com.dbc.vemserback.ecommerce.controller.login;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import org.springframework.http.MediaType;
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

import com.dbc.vemserback.ecommerce.dto.user.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.user.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserCreateDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserLoginDto;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Api(value = "1 - LoginAPI/Sign-Up API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"1 - LoginAPI/Sign-Up API"})
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public UserLoginDto auth(@RequestBody @Valid LoginDTO loginDTO) throws BusinessRuleException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                		loginDTO.getUsername(),
                		loginDTO.getPassword()
                );
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate);
        return token;
    }
    
    @PostMapping(path = "/sign-up", consumes = {MULTIPART_FORM_DATA_VALUE})
    public UserLoginDto signUp(@Valid @ModelAttribute(name = "data") CreateUserDTO createDTO, BindingResult bindingResult, @RequestPart(name = "file",required = false) MultipartFile file) throws BusinessRuleException {
    	if(bindingResult.hasErrors()) {
    		StringBuilder builder = new StringBuilder();
        	bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
    		throw new BusinessRuleException(builder.toString());
    	}
    	
    	UserCreateDTO userCreateDTO = objectMapper.convertValue(createDTO, UserCreateDTO.class);
    	userCreateDTO.setEmail(createDTO.getUsername());
        userCreateDTO.setGroups(Groups.USER);
        
    	UserLoginDto createUser = userService.createUser(userCreateDTO, file);
    	
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