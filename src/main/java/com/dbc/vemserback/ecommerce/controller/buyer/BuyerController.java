package com.dbc.vemserback.ecommerce.controller.buyer;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.QuotationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
@Slf4j
@Api(value = "4 - BuyerAPI API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"4 - BuyerAPI API"})
public class BuyerController {
	
    private final QuotationService quotationService;

    @PostMapping("/create/{topic-id}")
    public boolean create(@PathVariable("topic-id") Integer topicId, @RequestParam Double preco) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	log.info("#### USER #### ID -> '{}' : CREATED QUOTE", Integer.parseInt((String) userb));
    	
        return quotationService.createQuotation(topicId, preco, Integer.parseInt((String) userb));
    }

}
