package com.dbc.vemserback.ecommerce.controller.contributor;

import com.dbc.vemserback.ecommerce.dto.Item.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemFullDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api
public interface ContributorAPI {

    @ApiOperation(value = "Creates a topic", response = Integer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic created successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    Integer createTopic(@RequestBody TopicCreateDTO dto) throws BusinessRuleException;

    @ApiOperation(value = "Creates an item by topic", response = ItemFullDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item created successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    ItemFullDTO createItem(@PathVariable(name = "topic-id") Integer idTopic, @Valid @ModelAttribute(name = "data") ItemCreateDTO CreateDTO,
                           @RequestPart MultipartFile file, BindingResult bindingResult) throws BusinessRuleException;

    @ApiOperation(value = "Close a topic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic closed successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    void closeTopicById(@PathVariable(name = "topic-id") int idTopic) throws BusinessRuleException;

    @ApiOperation(value = "Delete a topic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic deleted successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    void deleteById(@PathVariable(name = "item-id") int itemId) throws BusinessRuleException;

    @ApiOperation(value = "Delete a topic by Id Topic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Topic deleted successfully"),
            @ApiResponse(code = 403, message = "you dont have the permission to access this resource"),
            @ApiResponse(code = 500, message = "One exception was throwed")
    })
    void deleteTopicById(@PathVariable(name = "topic-id") int topicId) throws BusinessRuleException;

    }

