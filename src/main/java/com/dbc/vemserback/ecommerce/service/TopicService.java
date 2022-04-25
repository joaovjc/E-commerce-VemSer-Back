package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseGetDTO;
import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationGetDTO;
import com.dbc.vemserback.ecommerce.dto.topic.FullTopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.dto.topic.TopicFinancierDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.custom.TopicrepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final TopicrepositoryImpl topicrepositoryImpl;
	private final ObjectMapper objectMapper;
	private final UserService userService;

	public Integer createTopic(TopicDTO dto, Integer userId) throws BusinessRuleException {

		UserEntity user = userService.findById(userId);
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.OPEN.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();

		System.out.println(StatusEnum.valueOf(StatusEnum.OPEN.name()));
		entity = topicRepository.save(entity);

		return entity.getTopicId();
	}

//	public boolean addPurchaseToTopic(int idUser, String idTopic, String idItem) {
//		return topicrepositoryImpl.updateAndAddItem(idTopic, idItem);
//	}

	public TopicDTO updateStatusToTopic(Integer idTopic, StatusEnum status) throws BusinessRuleException {
		TopicEntity topic = topicRepository.findById(idTopic).orElseThrow((() -> new BusinessRuleException("Topic not found")));

		topic.setStatus(status);
		return objectMapper.convertValue(topicRepository.save(topic), TopicDTO.class);
	}


	public List<TopicEntity> listTopics() {
		return topicRepository.findAll();
	}

	public List<FullTopicDTO> listTopicsFull() {
		return new ArrayList<>(topicRepository.findAll().stream().map(this::getTopicFull).toList());
	}

	private FullTopicDTO getTopicFull(TopicEntity topicEntity) {
		FullTopicDTO fullTopicDTO = objectMapper.convertValue(topicEntity, FullTopicDTO.class);
		fullTopicDTO.setPurchases(topicEntity.getPurchases().stream().map(purchase -> objectMapper.convertValue(purchase, PurchaseGetDTO.class)).collect(Collectors.toList()));
		fullTopicDTO.setQuotations(topicEntity.getQuotations().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationGetDTO.class)).collect(Collectors.toList()));
		return fullTopicDTO;
	}

	public TopicEntity topicById(Integer topicId) throws BusinessRuleException{
		return topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("Topic not found")));
	}

//	public List<String> purchasesByIdTopic(String topicId, int idUser){
//		return topicRepository.findAllPurchasesByIdAndIdUser(topicId, idUser);
//	}
	
	public Page<TopicAgreg> listAllTopicsByUserId(int idUser, int page) {
		PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.Direction.ASC,
                "title");
		return topicRepository.findAllByUserId(idUser, pageRequest);
	}

	public String updateFinancierTopic(TopicFinancierDTO topicFinancierDTO) throws BusinessRuleException {
		TopicEntity topic = topicRepository.findById(topicFinancierDTO.getTopicId()).orElseThrow((() -> new BusinessRuleException("Topic not found")));
		if (topicFinancierDTO.getStatus()) {
			topic.setStatus(StatusEnum.CLOSED);
			topicRepository.save(topic);
			return "Topic closed";
		} else {
			topic.setStatus(StatusEnum.FINANCIALLY_REPROVED);
			topicRepository.save(topic);
			return "Topic financially reproved";
		}

	}
}
