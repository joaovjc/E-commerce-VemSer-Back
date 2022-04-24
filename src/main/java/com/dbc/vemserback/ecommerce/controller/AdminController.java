package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.dto.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.UserAdmDto;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
import com.dbc.vemserback.ecommerce.dto.UserPageDTO;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.security.TokenService;
import com.dbc.vemserback.ecommerce.service.TopicService;
import com.dbc.vemserback.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
    public void admSetGroupUser( @RequestParam Groups groups, @RequestParam Integer idUser) throws BusinessRuleException {
        userService.updateUserbyAdmin(groups, idUser);
    }

    @GetMapping("/adm-get-all-users")
    public Page<UserPageDTO> admGetAllUsers(int page){
        return userService.listUsersForAdmin(page);
    }
}
