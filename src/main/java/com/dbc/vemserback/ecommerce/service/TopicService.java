package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseGetDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationGetDTO;
import com.dbc.vemserback.ecommerce.dto.topic.FullTopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicFinancierDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final ObjectMapper objectMapper;
	private final UserService userService;

	public Integer createTopic(TopicCreateDTO dto, Integer userId) throws BusinessRuleException {

		UserEntity user = userService.findById(userId);
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.OPEN.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();

		System.out.println(StatusEnum.valueOf(StatusEnum.OPEN.name()));
		entity = topicRepository.save(entity);

		return entity.getTopicId();
	}

	public TopicCreateDTO updateStatusToTopic(Integer idTopic, StatusEnum status) throws BusinessRuleException {
		TopicEntity topic = topicRepository.findById(idTopic).orElseThrow((() -> new BusinessRuleException("Topic not found")));

		topic.setStatus(status);
		return objectMapper.convertValue(topicRepository.save(topic), TopicCreateDTO.class);
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
	
	public Page<TopicDTO> listAllTopicsByUserId(int idUser, int page) {
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

	public void openTopic(int idTopic, int userId) throws BusinessRuleException {
		TopicEntity findById = this.findById(idTopic);
		if(findById.getUserId()!=userId)throw new BusinessRuleException("The topic is not owned by you, thus you cannot change it!!!");
		if(findById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("the topic was already opened!!!");
		BigDecimal bigDecimal = BigDecimal.ZERO;
		findById.getPurchases().forEach(it->bigDecimal.add(it.getValue()));
		findById.setTotalValue(bigDecimal);
		findById.setStatus(StatusEnum.OPEN);
		this.topicRepository.save(findById);
	}
	
	private TopicEntity findById(int idTopic) throws BusinessRuleException {
		return this.topicRepository.findById(idTopic).orElseThrow(()->new BusinessRuleException("The topic was not found"));
	}
}
