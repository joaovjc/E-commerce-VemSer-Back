package com.dbc.vemserback.ecommerce.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contributor")
@RequiredArgsConstructor
public class ContributorController {
	private final TopicService topicService;
	private final PurchaseService purchaseService;
	
	@PostMapping("/create-topic")
	public Integer createTopic(@RequestBody TopicCreateDTO dto) throws BusinessRuleException {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return topicService.createTopic(dto,Integer.parseInt((String) userb));
	}

    @PostMapping(path = "/create-item/{topic-id}", consumes = {MULTIPART_FORM_DATA_VALUE})
	public void createItem(@PathVariable(name = "topic-id") Integer idTopic, @Valid @ModelAttribute(name = "data") ItemCreateDTO CreateDTO,
                              @RequestPart MultipartFile file, BindingResult bindingResult) throws BusinessRuleException, InterruptedException {
    	if(bindingResult.hasErrors()) {
    		StringBuilder builder = new StringBuilder();
        	bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
    		throw new BusinessRuleException(builder.toString());
    	}
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        purchaseService.createPurchase(CreateDTO, file, Integer.parseInt((String) userb), idTopic);
	}
    
    @PutMapping("/update-status/{topic-id}")
    public void closeTopicById(@PathVariable(name = "topic-id") int idTopic) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	this.topicService.openTopic(idTopic, Integer.parseInt((String) userb));
    }
    
}
