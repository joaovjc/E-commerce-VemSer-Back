package com.dbc.vemserback.ecommerce.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.Item.ItemDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.ItemService;
import com.dbc.vemserback.ecommerce.service.QuotationService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {
	private final TopicService topicService;
	private final ItemService purchaseService;
	private final QuotationService quotationService;

	@GetMapping("/items/{topic-id}")
	public List<ItemDTO> allPurchasesByTopicId(@PathVariable("topic-id") Integer topicId) {
		return purchaseService.listPurchasesByTopicId(topicId);
	}

	@GetMapping("/quotation/{topic-id}")
	public List<QuotationByTopicDTO> getQuotationByTopic(@PathVariable("topic-id") Integer idTopic) throws BusinessRuleException {
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		return quotationService.quotationsByTopic(idTopic, authorities);
	}
	
	@GetMapping("/topics")
	public Page<TopicDTO> allTopics(@RequestParam int page, @RequestParam(required = false) Integer topics, @RequestParam(required = false) String title) throws BusinessRuleException {
		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.topicService.getTopics(authorities, Integer.parseInt((String) userb), page, topics, title);
	}
	
//	@GetMapping("/topic-by-titulo/{title}")
//	public Page<TopicDTO> allTopicsByTitle(@RequestParam int page,@PathVariable("title") String title) throws BusinessRuleException {
//		@SuppressWarnings("unchecked")
//		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		return this.topicService.getTopicsByTitle(authorities, title, page);
//	}
	
//	@GetMapping("/topic-by-titulo/{title}")
//	public List<TopicDTO> allTopicsByTitle(@PathVariable("title") String title) throws BusinessRuleException {
//		@SuppressWarnings("unchecked")
//		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		return this.topicService.getTopicsByTitle(authorities, title);
//	}
}
