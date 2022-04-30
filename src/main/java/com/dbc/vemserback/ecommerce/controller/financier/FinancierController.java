package com.dbc.vemserback.ecommerce.controller.financier;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
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
@Api(value = "6 - Financier API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"6 - Financier API"})
public class FinancierController {

    private final TopicService topicService;

    @PutMapping("/update-status/{topicId}/{status}")
    public String updateFinancierTopic(@PathVariable Integer topicId, @PathVariable Boolean status) throws BusinessRuleException {
        return topicService.updateFinancierTopic(topicId, status);
    }
}
