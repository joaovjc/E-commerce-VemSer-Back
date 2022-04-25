package com.dbc.vemserback.ecommerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Manager")
@RequiredArgsConstructor
public class ManagerController {
	private final QuotationService quotationService;
	
	@PostMapping("/aproveQuotation")
    public QuotationDTO aproveQuotation(Integer idTopic, Integer quotationId) throws BusinessRuleException {
        return quotationService.aproveQuotation(idTopic, quotationId);
    }

    @PostMapping("/reproveAllQuotations")
    public void reproveAllQuotations(Integer idTopic) throws BusinessRuleException {
        quotationService.reproveAllQuotations(idTopic);
    }
	
}
