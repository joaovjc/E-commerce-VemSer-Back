package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseAgreg;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.mongo.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final FileService fileService;
	private final TopicService topicService;

	public boolean createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, String idTopic) throws BusinessRuleException {
		if(file==null)throw new BusinessRuleException("the item file cannot be null");
		String originalFilename = file.getOriginalFilename();
		PurchaseEntity build = PurchaseEntity.builder().name(purchaseDTO.getName())
				.totalValue(new BigDecimal(purchaseDTO.getPrice())).fileName(originalFilename).file(fileService.convertToByte(file)).build();
		PurchaseEntity save = purchaseRepository.save(build);
		return topicService.addPurchaseToTopic(idUser, idTopic, save.getListId());
	}

	public List<PurchaseAgreg> listAllTopics(String idTopic, int idUser) {
		List<String> purchasesByIdTopic = topicService.purchasesByIdTopic(idTopic, idUser);
		return purchaseRepository.findAllById(purchasesByIdTopic);
	}


}
