package com.dbc.vemserback.ecommerce.dto;


import lombok.Data;

@Data
public class UserCreateDTO {


    private String fullName;

    private String email;

    private String password;
}
