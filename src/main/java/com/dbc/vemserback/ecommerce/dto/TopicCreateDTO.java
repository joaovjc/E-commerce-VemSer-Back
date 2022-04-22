package com.dbc.vemserback.ecommerce.dto;

import java.util.List;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TopicCreateDTO {
	
	private String name;
	private Double totalPrice;
	
}
