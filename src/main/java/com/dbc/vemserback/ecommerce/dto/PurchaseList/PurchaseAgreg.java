package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseAgreg {
	
	private String name;
	private Double totalValue;
	private byte[] file;
	
}
