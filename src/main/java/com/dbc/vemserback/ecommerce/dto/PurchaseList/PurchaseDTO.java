package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseDTO {
	private String name;
	private String description;
	private Double price;
}
