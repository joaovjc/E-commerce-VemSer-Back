package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserCreateDTO;
import com.dbc.vemserback.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/get-users")
    public List<LoginDTO> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/save-user")
    public UserCreateDTO saveUser(@RequestBody UserCreateDTO userCreateDTO) throws Exception {
       return userService.createUser(userCreateDTO);
    }


}
