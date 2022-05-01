package com.dbc.vemserback.ecommerce.controller.financier;

import org.springframework.web.bind.annotation.PathVariable;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface FinancierAPI {

    @ApiOperation(value = "Updates a topic", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic updated successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    String updateFinancierTopic(@PathVariable Integer topicId, @PathVariable Boolean status) throws BusinessRuleException;
}
