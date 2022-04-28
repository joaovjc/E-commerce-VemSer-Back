package com.dbc.vemserback.ecommerce.dto.Item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemFullDTO{
	
	private Integer purchaseId;
	private String itemName;
	private String description;
	private BigDecimal value;
	private String file;
	
}
