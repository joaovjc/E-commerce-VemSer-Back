package com.dbc.vemserback.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.UserAdmDto;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
import com.dbc.vemserback.ecommerce.dto.UserPageDTO;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;


    @PostMapping("/adm-creat-user")
    public UserLoginDto admCreateUser(@Valid @ModelAttribute(name = "data") UserAdmDto userAdmDTO, @RequestPart(name = "file",required = false) MultipartFile file, BindingResult bindingResult) throws BusinessRuleException {
        if(bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
            throw new BusinessRuleException(builder.toString());
        }

        UserLoginDto createUser = userService.createUser(userAdmDTO, file);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        createUser.getUsername(),
                        userAdmDTO.getPassword()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserLoginDto token = tokenService.getToken(authenticate,createUser);
        return token;
    }

    @PutMapping("/adm-set-group-user")
    public void admSetGroupUser(@RequestParam Groups groups, @RequestParam Integer idUser) throws BusinessRuleException {
        userService.updateUserbyAdmin(groups, idUser);
    }

    @GetMapping("/adm-get-all-users")
    public Page<UserPageDTO> admGetAllUsers(int page){
        return userService.listUsersForAdmin(page);
    }
    
    @GetMapping("/adm-get-all-users-by-full-name")
    public List<UserEntity> admGetAllUsersByFullName(@RequestParam String nome){
        return userService.getByFullName(nome);
    }
}
