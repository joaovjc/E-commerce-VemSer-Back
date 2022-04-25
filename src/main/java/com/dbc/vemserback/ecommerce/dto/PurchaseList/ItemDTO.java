package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemDTO {
	
	private String itemName;
	private String description;
	private BigDecimal value;
	private byte[] file;
	
}
