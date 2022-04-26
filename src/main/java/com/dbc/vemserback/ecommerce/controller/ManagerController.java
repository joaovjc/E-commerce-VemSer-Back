package com.dbc.vemserback.ecommerce.controller;

import org.springframework.web.bind.annotation.PathVariable;
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

    //Manager
	@PostMapping("/aproveQuotation/{quotation-id}")
    public QuotationDTO aproveQuotation( @PathVariable("quotation-id") Integer quotationId) throws BusinessRuleException {
       return quotationService.aproveQuotation(quotationId);
    }

    //Manager
    @PostMapping("/reproveAllQuotations/{topic-id}")
    public void reproveAllQuotations(@PathVariable("topic-id") Integer idTopic) throws BusinessRuleException {
        quotationService.reproveAllQuotations(idTopic);
    }
	
}
