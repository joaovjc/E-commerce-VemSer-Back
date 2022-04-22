package com.dbc.vemserback.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
	
	public void createTopic(@RequestBody TopicDTO dto)
			throws BusinessRuleException {
		
		
		
	}
	
	@PostMapping("/create-item/{topic-id}")
	public void createItem(@PathVariable(name = "topic-id") Integer idTopic,@Valid @ModelAttribute(name = "data") PurchaseDTO CreateDTO,
			@RequestPart(name = "file", required = false) MultipartFile file, BindingResult bindingResult)
			throws BusinessRuleException {
		
		
		
	}
}
