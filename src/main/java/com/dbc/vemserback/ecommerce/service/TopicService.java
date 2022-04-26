package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
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
		if (dto.getTitle()==null) throw new BusinessRuleException("Topic name cannot be null");
		UserEntity user = userService.findById(userId);
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.CREATING.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();

		entity = topicRepository.save(entity);

		return entity.getTopicId();
	}

	//todo generalizar
	public TopicCreateDTO updateStatusToTopic(Integer idTopic, StatusEnum status) throws BusinessRuleException {
		TopicEntity topic = topicRepository.findById(idTopic).orElseThrow((() -> new BusinessRuleException("Topic not found")));
		topic.setStatus(status);
		return objectMapper.convertValue(topicRepository.save(topic), TopicCreateDTO.class);
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

	//todo generalizar
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
		if(findById.getPurchases().size()<1)throw new BusinessRuleException("the topic must have at least one");
		double sum = findById.getPurchases().stream().mapToDouble(item->item.getValue().doubleValue()).sum();
		findById.setTotalValue(new BigDecimal(sum));
		findById.setStatus(StatusEnum.OPEN);
		this.topicRepository.save(findById);
	}
	
	public Page<TopicDTO> getTopics(List<SimpleGrantedAuthority> authorities, int userId, int page) throws BusinessRuleException {
		List<String> collect = authorities.stream().map(smp -> smp.getAuthority()).collect(Collectors.toList());
		if(collect.contains("ROLE_MANAGER")) {
			return this.findAllByStatus(StatusEnum.OPEN, page);
		}else if(collect.contains("ROLE_FINANCIER")) {
			return this.findAllByStatus(StatusEnum.MANAGER_APPROVED, page);
		}else if(collect.contains("ROLE_BUYER")) {
			return this.findAllByDifferenStatus(StatusEnum.CREATING, page);
		}else {
			return this.listAllTopicsByUserId(userId,page);
		}
	}
	
	private Page<TopicDTO> findAllByDifferenStatus(StatusEnum enumTopic, int page) {
		PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.Direction.ASC,
                "title");
		return this.topicRepository.findAllByStatusDifferent(enumTopic, pageRequest);
	}

	private Page<TopicDTO> findAllByStatus(StatusEnum enumTopic, int page) throws BusinessRuleException{
		PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.Direction.ASC,
                "title");
		return this.topicRepository.findAllByStatus(enumTopic, pageRequest);
	}
	
	private TopicEntity findById(int idTopic) throws BusinessRuleException {
		return this.topicRepository.findById(idTopic).orElseThrow(()->new BusinessRuleException("The topic was not found"));
	}

	public Page<TopicDTO> getTopicsByTitle(List<SimpleGrantedAuthority> authorities, String title, int page) {
		PageRequest pageRequest = PageRequest.of(
                page,
                10,
                Sort.Direction.ASC,
                "title");
		return this.topicRepository.findAllByTitle(title, pageRequest);
	}

}
