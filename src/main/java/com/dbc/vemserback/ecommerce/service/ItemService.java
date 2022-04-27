package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.Item.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemFullDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	private final PurchaseRepository purchaseRepository;
	private final FileService fileService;
	private final TopicService topicService;

	public ItemFullDTO createPurchase(ItemCreateDTO purchaseDTO, MultipartFile file, int idUser, Integer idTopic) throws BusinessRuleException {
		if(file==null)throw new BusinessRuleException("the item file cannot be null");
		String originalFilename = file.getOriginalFilename();

		TopicEntity topicEntity = topicService.topicById(idTopic);
		if(topicEntity.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("the topic is not on creating status!!!");
		PurchaseEntity build = PurchaseEntity.builder().itemName(purchaseDTO.getName()).description(purchaseDTO.getDescription())
				.value(purchaseDTO.getPrice()).fileName(originalFilename).file(fileService.convertToByte(file)).topicId(idTopic).topicEntity(topicEntity).build();
		PurchaseEntity save = purchaseRepository.save(build);
		
		return ItemFullDTO.builder()
				.description(save.getDescription())
				.file(new String(save.getFile()))
				.itemName(save.getItemName())
				.itemId(save.getPurchaseId())
				.value(save.getValue()).build();
	}

	public List<ItemDTO> listPurchasesByTopicId(Integer topicId) {
		return purchaseRepository.findAllByTopicId(topicId).stream().map(ent->{
			return ItemDTO.builder()
					.description(ent.getDescription())
					.itemName(ent.getItemName())
					.file(ent.getFile()!=null?new String(ent.getFile()):null)
					.value(ent.getValue()).build();
		}).collect(Collectors.toList());
	}

	public void deleteById(int idItem, int userId) throws BusinessRuleException {
		PurchaseEntity byId = this.getById(idItem);
		if(byId.getTopicEntity().getUserId()!=userId)throw new BusinessRuleException("this item isnt from the autenticated user");
		if(byId.getTopicEntity().getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("the topic was already closed for changes");
		this.purchaseRepository.delete(byId);
	}
	
	private PurchaseEntity getById(int idItem) throws BusinessRuleException {
		return this.purchaseRepository.findById(idItem).orElseThrow(()-> new BusinessRuleException("item not found, please reload the page"));
	}

}
