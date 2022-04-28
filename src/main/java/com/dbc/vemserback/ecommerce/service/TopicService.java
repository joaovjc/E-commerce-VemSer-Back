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
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final UserService userService;

	public Integer createTopic(TopicCreateDTO dto, Integer userId) throws BusinessRuleException {
		if (dto.getTitle()==null) throw new BusinessRuleException("Topic name cannot be null");
		if(topicRepository.findByTitle(dto.getTitle())!=null)throw new BusinessRuleException("Topic name already exists");
		UserEntity user = userService.findById(userId);
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.CREATING.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();
		
		entity = topicRepository.save(entity);

		return entity.getTopicId();
	}

	public TopicEntity topicById(Integer topicId) throws BusinessRuleException{
		return topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("Topic not found")));
	}
	
	public Page<TopicDTO> listAllTopicsByUserId(int idUser, int page, Integer topics, String title) {
		PageRequest pageRequest = PageRequest.of(
                page,
                ((topics==null)?4:topics),
                Sort.Direction.ASC,
                "title");
		if(title!=null) {
			return topicRepository.findAllByUserId(idUser, title, pageRequest);
		}else {
			return topicRepository.findAllByUserId(idUser, pageRequest);
		}
	}

	public String updateFinancierTopic(Integer topicId, Boolean status) throws BusinessRuleException {
		TopicEntity topic = topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("Topic not found")));
		if (status) {
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
		if(findById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("The topic was already opened!!!");
		if(findById.getPurchases().size()<1)throw new BusinessRuleException("he topic must have at least one");
		double sum = findById.getPurchases().stream().mapToDouble(item->item.getValue().doubleValue()).sum();
		findById.setTotalValue(new BigDecimal(sum));
		findById.setStatus(StatusEnum.OPEN);
		this.topicRepository.save(findById);
	}
	
	public Page<TopicDTO> getTopics(List<SimpleGrantedAuthority> authorities, int userId, int page, Integer topics, String title) throws BusinessRuleException {
		List<String> collect = authorities.stream().map(smp -> smp.getAuthority()).collect(Collectors.toList());
		if(collect.contains("ROLE_MANAGER")) {
			return this.findAllByStatus(StatusEnum.OPEN, page, topics, title);
		}else if(collect.contains("ROLE_FINANCIER")) {
			return this.findAllByStatus(StatusEnum.MANAGER_APPROVED, page, topics, title);
		}else if(collect.contains("ROLE_BUYER")) {
			return this.findAllByDifferenStatus(StatusEnum.CREATING, page, topics, title);
		}else {
			return this.listAllTopicsByUserId(userId,page, topics, title);
		}
	}
	
	private Page<TopicDTO> findAllByDifferenStatus(StatusEnum enumTopic, int page, Integer topics, String title) {
		PageRequest pageRequest = PageRequest.of(
                page,
                ((topics==null)?4:topics),
                Sort.Direction.ASC,
                "title");
		if(title!=null) {
			return this.topicRepository.findAllByStatusDifferent(enumTopic, title, pageRequest);
		}else {
			return this.topicRepository.findAllByStatusDifferent(enumTopic, pageRequest);
		}
	}

	private Page<TopicDTO> findAllByStatus(StatusEnum enumTopic, int page, Integer topics, String title) throws BusinessRuleException{
		PageRequest pageRequest = PageRequest.of(
                page,
               ((topics==null)?4:topics),
                Sort.Direction.ASC,
                "title");
		if(title!=null) {
			return this.topicRepository.findAllByStatus(enumTopic, title, pageRequest);
		}else {
			return this.topicRepository.findAllByStatus(enumTopic, pageRequest);
		}
	}
	
	private TopicEntity findById(int idTopic) throws BusinessRuleException {
		return this.topicRepository.findById(idTopic).orElseThrow(()->new BusinessRuleException("The topic was not found"));
	}

	public Page<TopicDTO> getTopicsByTitle(List<SimpleGrantedAuthority> authorities, String title, int page) {
		PageRequest pageRequest = PageRequest.of(
                page,
                3,
                Sort.Direction.ASC,
                "title");
		return this.topicRepository.findAllByTitle(title, pageRequest);
	}
//	public List<TopicDTO> getTopicsByTitle(List<SimpleGrantedAuthority> authorities, String title) {
//		return this.topicRepository.findAllByTitle(title);
//	}

	public void save(TopicEntity topic) {
		this.topicRepository.save(topic);
	}
}
