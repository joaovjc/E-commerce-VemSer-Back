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
		TopicEntity findById = this.topicById(idTopic);
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
		PageRequest pageRequest = PageRequest.of(
                page,
                ((topics==null)?4:topics),
                Sort.Direction.ASC,
                "title");
		if(collect.contains("ROLE_MANAGER")) {
			if(title!=null) {
				return this.topicRepository.findAllByStatus(StatusEnum.OPEN, title, pageRequest);
			}else {
				return this.topicRepository.findAllByStatus(StatusEnum.OPEN, pageRequest);
			}
		}else if(collect.contains("ROLE_FINANCIER")) {
			if(title!=null) {
				return this.topicRepository.findAllByStatus(StatusEnum.MANAGER_APPROVED, title, pageRequest);
			}else {
				return this.topicRepository.findAllByStatus(StatusEnum.MANAGER_APPROVED, pageRequest);
			}
		}else if(collect.contains("ROLE_BUYER")) {
			if(title!=null) {
				return this.topicRepository.findAllByStatusDifferent(StatusEnum.CREATING, title, pageRequest);
			}else {
				return this.topicRepository.findAllByStatusDifferent(StatusEnum.CREATING, pageRequest);
			}
		}else {
			if(title!=null) {
				return topicRepository.findAllByUserId(userId, title, pageRequest);
			}else {
				return topicRepository.findAllByUserId(userId, pageRequest);
			}
		}
	}
	
	public TopicEntity topicById(Integer topicId) throws BusinessRuleException{
		return topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("Topic not found")));
	}
	
	public void save(TopicEntity topic) {
		this.topicRepository.save(topic);
	}

	public void deleteById(int topicId, int userId) throws BusinessRuleException {
		TopicEntity topicById = this.topicById(topicId);
		if(topicById.getUser().getUserId()!=userId)throw new BusinessRuleException("esse topico não pertence a esse user");
		if(topicById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("topico não pode ser deletado nesse status");
		this.topicRepository.deleteById(topicId);
	}
}
