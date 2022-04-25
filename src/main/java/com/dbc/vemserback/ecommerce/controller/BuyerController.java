package com.dbc.vemserback.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {
	
    private final QuotationService quotationService;

    @PostMapping("/create")
    public String create(QuotationCreateDTO quotationCreateDTO) throws BusinessRuleException {
        return quotationService.createQuotation(quotationCreateDTO);
    }

}
