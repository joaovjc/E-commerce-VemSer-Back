package com.dbc.vemserback.ecommerce.dto.Item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCreateDTO {
	private String name;
	private String description;
	private BigDecimal price;
}
