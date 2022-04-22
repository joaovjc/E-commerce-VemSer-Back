package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

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
	private BigDecimal price;
	MultipartFile file;
	
}
