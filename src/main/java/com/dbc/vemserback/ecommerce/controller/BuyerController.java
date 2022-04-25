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

//    @GetMapping("/getAll")
//    public List<QuotationDTO> getAll() {
//        return quotationService.quotationList();
//    }

    @PostMapping("/create")
    public String create(QuotationCreateDTO quotationCreateDTO) throws BusinessRuleException {
        return quotationService.createQuotation(quotationCreateDTO);
    }

//    @PostMapping("/aproveQuotation")
//    public QuotationDTO aproveQuotation(Integer idTopic, Integer quotationId) throws BusinessRuleException {
//        return quotationService.aproveQuotation(idTopic, quotationId);
//    }
//
//    @PostMapping("/reproveAllQuotations")
//    public void reproveAllQuotations(Integer idTopic) throws BusinessRuleException {
//        quotationService.reproveAllQuotations(idTopic);
//    }

//    @GetMapping("/getQuotationByTopic")
//    public List<QuotationByTopicDTO> getQuotationByTopic(Integer idTopic) throws BusinessRuleException {
//        return quotationService.quotationsByTopic(idTopic);
//    }

}
