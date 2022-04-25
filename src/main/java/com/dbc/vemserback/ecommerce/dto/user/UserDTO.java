package com.dbc.vemserback.ecommerce.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends CreateUserDTO {

    private Integer userId;
}