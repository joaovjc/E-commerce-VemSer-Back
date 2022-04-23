package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.repository.mongo.TopicRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.custom.TopicrepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final TopicrepositoryImpl topicrepositoryImpl;

	public String createTopic(TopicDTO dto, Integer userId) {

		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.OPEN)
				.purchases(new ArrayList<String>()).title(dto.getTitle()).totalValue(BigDecimal.ZERO)
				.userId(userId).build();

		entity = topicRepository.insert(entity);

		return entity.getTopicId();
	}

	public boolean addPurchaseToTopic(int idUser, String idTopic, String idItem) {
		return topicrepositoryImpl.updateAndAddItem(idTopic, idItem);
	}

	public List<TopicEntity> listTopics() {
		return topicRepository.findAll();
	}

}
