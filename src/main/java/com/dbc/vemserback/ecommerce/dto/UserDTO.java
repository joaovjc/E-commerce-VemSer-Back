package com.dbc.vemserback.ecommerce.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends CreateUserDTO{

    private Integer userId;
}
