package com.dbc.vemserback.ecommerce.dto.Item;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCreateDTO {
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Double price;
}
