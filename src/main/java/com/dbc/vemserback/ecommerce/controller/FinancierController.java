package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.dto.topic.TopicFinancierDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financier")
@RequiredArgsConstructor
public class FinancierController {

    private final TopicService topicService;


    @PutMapping("/update-status/{topicId}/{status}")
    public String updateFinancierTopic(@RequestParam Integer topicId, @RequestParam Boolean status) throws BusinessRuleException {
        return topicService.updateFinancierTopic(topicId, status);
    }
}
