package com.dbc.vemserback.ecommerce.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import lombok.RequiredArgsConstructor;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/Manager")
@RequiredArgsConstructor
public class ManagerController {
	private final QuotationService quotationService;
	
	@PostMapping("/aproveQuotation/{quotation-id}")
    public QuotationDTO aproveQuotation( @PathParam("quotation-id") Integer quotationId) throws BusinessRuleException {
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return quotationService.aproveQuotation(authorities, quotationId);
    }

    @PostMapping("/reproveAllQuotations/{topic-id}")
    public void reproveAllQuotations(List<SimpleGrantedAuthority> authorities, @PathParam("topic-id") Integer idTopic) throws BusinessRuleException {
        quotationService.reproveAllQuotations(authorities, idTopic);
    }
	
}
