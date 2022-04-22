package com.dbc.vemserback.ecommerce.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main-page")
@RequiredArgsConstructor
@Validated
public class MainPageController {

//    private final UserService userService;

    @GetMapping("/get-hello")
    public String getUsers() {
        return "hello main page";
    }

}
