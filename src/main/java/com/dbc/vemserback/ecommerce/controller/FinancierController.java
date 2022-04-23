package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.dto.topic.TopicFinancierDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/financier")
@RequiredArgsConstructor
public class FinancierController {

    private final TopicService topicService;


    @PutMapping("/update-status")
    public String updateFinancierTopic(TopicFinancierDTO topicFinancierDTO) throws BusinessRuleException {
        return topicService.updateFinancierTopic(topicFinancierDTO);
    }
}
