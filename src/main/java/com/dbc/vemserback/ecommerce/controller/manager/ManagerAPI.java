package com.dbc.vemserback.ecommerce.controller.manager;

import org.springframework.web.bind.annotation.PathVariable;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
public interface ManagerAPI {

    @ApiOperation(value = "Approve quotation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully aproved quotation"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    void aproveQuotation(@PathVariable("quotation-id") Integer quotationId) throws BusinessRuleException;


    @ApiOperation(value = "Reprove all quotations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully reproved all quotation"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    void reproveAllQuotations(@PathVariable("topic-id") Integer idTopic) throws BusinessRuleException;
}
