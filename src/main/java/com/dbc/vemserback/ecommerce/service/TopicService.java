package com.dbc.vemserback.ecommerce.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.repository.mongo.PurchaseRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.TopicRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.custom.TopicrepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final PurchaseRepository purchaseRepository;
	private final TopicrepositoryImpl topicrepositoryImpl;
	private final FileService fileService;
//	private final ObjectMapper objectMapper;

	public String createTopic(TopicDTO dto, Integer userId) {

		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.OPEN)
				.purchases(new ArrayList<String>()).title(dto.getTitle()).totalValue(BigDecimal.ZERO)
				.userId(userId).build();

		entity = topicRepository.insert(entity);

		return entity.getTopicId();
	}

	public boolean createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, String idTopic) {
		String originalFilename = file.getOriginalFilename();
		
		byte[] bytes=null;
		try {
			bytes = fileService.convertToByte(file, originalFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PurchaseEntity build = PurchaseEntity.builder().name(purchaseDTO.getName())
				.totalValue(new BigDecimal(purchaseDTO.getPrice())).fileName(originalFilename).file(bytes)
				.build();
		
		PurchaseEntity save = purchaseRepository.save(build);
		
		return topicrepositoryImpl.updateAndAddItem(idTopic, save.getListId());
	}

	public List<TopicEntity> listTopics() {
		return topicRepository.findAll();
	}

}
