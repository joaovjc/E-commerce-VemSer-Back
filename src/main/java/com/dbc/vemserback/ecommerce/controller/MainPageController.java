package com.dbc.vemserback.ecommerce.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {
	private final TopicService topicService;
	private final PurchaseService purchaseService; 
	
	@GetMapping("/topics")
	public List<TopicAgreg> allTopics() {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.topicService.listAllTopics(Integer.parseInt((String) userb));
	}
	
//	@GetMapping("/topics/itens/{id-topic}")
	public Object allItens(@PathVariable(name = "id-topic") String idTopic) {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
		return this.purchaseService.listAllTopics(idTopic,Integer.parseInt((String) userb));
	}
	
}
