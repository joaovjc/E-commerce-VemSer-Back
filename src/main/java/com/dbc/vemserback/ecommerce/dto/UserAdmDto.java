package com.dbc.vemserback.ecommerce.dto;

import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAdmDto {
        @NotEmpty
        private String fullName;
        @NotNull
        @Email
        private String email;
        @NotNull
        @ValidPassword
        private String password;
        @NotNull
        private Groups groups;
}
