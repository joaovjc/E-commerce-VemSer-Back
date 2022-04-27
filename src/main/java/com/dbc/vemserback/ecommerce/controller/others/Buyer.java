package com.dbc.vemserback.ecommerce.controller.others;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface Buyer {

	@ApiOperation(value = "Recives the Topic id and the price of the quote")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "successfully creates the quote"),
			@ApiResponse(code = 403, message = "you dont have the permission to create a quote"),
			@ApiResponse(code = 500, message = "One exception was throwed") 
	})
	public boolean create(Integer topicId,Double preco) throws BusinessRuleException;
	
}
