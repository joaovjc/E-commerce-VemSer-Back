package com.dbc.vemserback.ecommerce.controller.main_page;

import com.dbc.vemserback.ecommerce.dto.Item.ItemDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api
public interface MainPageAPI {

    @ApiOperation(value = "Receives a list of items by topic", response = ItemDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    List<ItemDTO> allPurchasesByTopicId(@PathVariable("topic-id") Integer topicId) throws BusinessRuleException;

    @ApiOperation(value = "Receive a list of quotations by topic", response = QuotationByTopicDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    List<QuotationByTopicDTO> getQuotationByTopic(@PathVariable("topic-id") Integer idTopic) throws BusinessRuleException;

    @ApiOperation(value = "Receive a page of topics", response = TopicDTO.class, responseContainer = "Page")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved page"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    Page<TopicDTO> allTopics(@RequestParam int page, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String title) throws BusinessRuleException;


}
