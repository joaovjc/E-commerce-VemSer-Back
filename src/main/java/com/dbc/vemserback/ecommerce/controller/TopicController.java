package com.dbc.vemserback.ecommerce.controller;

import javax.validation.Valid;

import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
	private final TopicService topicService;
	private final PurchaseService purchasesService;
	
	@PostMapping("/create-topic")
	public String createTopic(@RequestBody TopicDTO dto){
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return topicService.createTopic(dto,Integer.parseInt((String) userb));
	}
	
	@PostMapping("/create-item/{topic-id}")
	public void createItem(@PathVariable(name = "topic-id") String idTopic,@Valid @ModelAttribute(name = "data") PurchaseDTO CreateDTO,
			@RequestPart(name = "file", required = false) MultipartFile file, BindingResult bindingResult)
			throws BusinessRuleException {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		topicService.createPurchase(CreateDTO, file, Integer.parseInt((String) userb), idTopic);
		System.out.println(CreateDTO.getName());
	}

	@GetMapping("/get-topics")
	public List<TopicEntity> listTopics(){
		return topicService.listTopics();
	}
}
