package com.dbc.vemserback.ecommerce.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {
	
    private final QuotationService quotationService;

    @PostMapping("/create")
    public QuotationEntity create(QuotationCreateDTO quotationCreateDTO) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return quotationService.createQuotation(quotationCreateDTO, Integer.parseInt((String) userb));
    }

}
