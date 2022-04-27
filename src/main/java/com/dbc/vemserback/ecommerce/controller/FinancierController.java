package com.dbc.vemserback.ecommerce.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/financier")
@RequiredArgsConstructor
public class FinancierController {

    private final TopicService topicService;


    @PutMapping("/update-status/{topicId}/{status}")
    public String updateFinancierTopic(@PathVariable Integer topicId, @PathVariable Boolean status) throws BusinessRuleException {
        return topicService.updateFinancierTopic(topicId, status);
    }
}
