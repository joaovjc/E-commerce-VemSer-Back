package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.ItensDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final FileService fileService;
	private final TopicService topicService;

	public String createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, Integer idTopic) throws BusinessRuleException {
		if(file==null)throw new BusinessRuleException("the item file cannot be null");
		String originalFilename = file.getOriginalFilename();

		TopicEntity topicEntity = topicService.topicById(idTopic);

		PurchaseEntity build = PurchaseEntity.builder().itemName(purchaseDTO.getName()).description(purchaseDTO.getDescription())
				.value(new BigDecimal(purchaseDTO.getPrice())).fileName(originalFilename).file(fileService.convertToByte(file)).topicId(idTopic).topicEntity(topicEntity).build();
		purchaseRepository.save(build);
		return "item adicionado com sucesso";
	}

	public List<ItensDTO> listPurchasesByTopicId(Integer topicId) {
		return purchaseRepository.findAllByTopicId(topicId);
	}


}
