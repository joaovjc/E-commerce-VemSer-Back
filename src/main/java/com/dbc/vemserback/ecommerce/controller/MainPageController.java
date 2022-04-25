package com.dbc.vemserback.ecommerce.controller;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseAgreg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {
	private final TopicService topicService;
	private final PurchaseService purchaseService; 
	
	@GetMapping("/topics")
	public Page<TopicAgreg> allTopics(@RequestParam int page) {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.topicService.listAllTopicsByUserId(Integer.parseInt((String) userb), page);
	}

	@GetMapping("/items/{topicId}")
	public List<PurchaseAgreg> allPurchasesByTopicId(@PathVariable("topicId") Integer topicId) {
		return purchaseService.listPurchasesByTopicId(topicId);
	}

}
