package com.dbc.vemserback.ecommerce.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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
		if (dto.getTitle()==null) throw new BusinessRuleException("o nome do Topico não pode ser nulo");
		if(topicRepository.findByTitle(dto.getTitle())!=null)throw new BusinessRuleException("este nome de topico já existe "+ dto.getTitle());
		UserEntity user = userService.findById(userId);
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.CREATING.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();
		
		entity = topicRepository.save(entity);

		return entity.getTopicId();
	}
	
	public String updateFinancierTopic(Integer topicId, Boolean status) throws BusinessRuleException {
		TopicEntity topic = this.topicById(topicId);
		if (status) {
			topic.setStatus(StatusEnum.CONCLUDED);
			topicRepository.save(topic);
			return "Concluded topic";
		} else {
			topic.setStatus(StatusEnum.FINANCIALLY_REPROVED);
			topicRepository.save(topic);
			return "Topic financially reproved";
		}
	}

	public void openTopic(int idTopic, int userId) throws BusinessRuleException {
		TopicEntity findById = this.topicById(idTopic);
		if(findById.getUserId()!=userId)throw new BusinessRuleException("voce não pode alterar um tópico que não é seu");
		if(findById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("o topico não pode ser alterado pois já foi aberto");
		if(findById.getPurchases().size()<1)throw new BusinessRuleException("o topico tem que ter pelo menos um item");
		
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
                Sort.by(new Order(Direction.DESC, "status"), 
                	    new Order(Direction.ASC, "date")));
		title = title==null?"":title;
		if(collect.contains("ROLE_MANAGER")) {
				return this.topicRepository.findAllByStatus(StatusEnum.OPEN, title, pageRequest);
		}else if(collect.contains("ROLE_FINANCIER")) {
				return this.topicRepository.findAllByStatus(StatusEnum.MANAGER_APPROVED, title, pageRequest);
		}else if(collect.contains("ROLE_BUYER")) {
				return this.topicRepository.findAllByStatusDifferent(StatusEnum.CREATING, title, pageRequest);
		}else {
				return topicRepository.findAllByUserId(userId, title, pageRequest);
		}
	}
	
	public TopicEntity topicById(Integer topicId) throws BusinessRuleException{
		return topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("topico não encontrado")));
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
