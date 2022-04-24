package com.dbc.vemserback.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPageDTO {

    private Integer userId;

    private String fullName;

    private String email;

    private String groups;
    
    private String image;

	public UserPageDTO(Integer userId, String fullName, String email, String groups) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.groups = groups;
	}
    
    
}
