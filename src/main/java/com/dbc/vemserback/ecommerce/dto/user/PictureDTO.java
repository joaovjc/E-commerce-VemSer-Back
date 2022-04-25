package com.dbc.vemserback.ecommerce.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PictureDTO {
	private byte[] picture;
    private Integer userId;
}
