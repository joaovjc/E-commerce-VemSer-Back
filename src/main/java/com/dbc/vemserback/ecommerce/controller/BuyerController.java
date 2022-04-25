package com.dbc.vemserback.ecommerce.controller;

import javax.websocket.server.PathParam;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {
	
    private final QuotationService quotationService;

    @PostMapping("/create/{topic-id}")
    public QuotationEntity create(@PathParam("topic-id") int topicId, @RequestBody Double preco) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return quotationService.createQuotation(topicId, preco, Integer.parseInt((String) userb));
    }

}
