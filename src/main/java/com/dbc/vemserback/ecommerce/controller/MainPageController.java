package com.dbc.vemserback.ecommerce.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.ItensDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import com.dbc.vemserback.ecommerce.service.QuotationService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main-page")
@RequiredArgsConstructor
public class MainPageController {
	private final TopicService topicService;
	private final PurchaseService purchaseService;
	private final QuotationService quotationService;

	// para os colaboradores
	@GetMapping("/topics-by-userid")
	public Page<TopicDTO> allTopicsByUserId(@RequestParam int page) {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.topicService.listAllTopicsByUserId(Integer.parseInt((String) userb), page);
	}

	// para todos
	@GetMapping("/items/{topic-id}")
	public List<ItensDTO> allPurchasesByTopicId(@PathVariable("topic-id") Integer topicId) {
		return purchaseService.listPurchasesByTopicId(topicId);
	}

	// para todos
	@GetMapping("/quotation/{topic-id}")
	public List<QuotationByTopicDTO> getQuotationByTopic(@PathVariable("topic-id") Integer idTopic) throws BusinessRuleException {
		return quotationService.quotationsByTopic(idTopic);
	}

	// TODO ->
	// Topic Por status (Para Comprador, Gestor, Financeiro)
	public Page<TopicDTO> allTopicsByStatus() {
		return null;
	}
}
