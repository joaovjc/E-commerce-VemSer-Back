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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final UserService userService;

	public Integer createTopic(TopicCreateDTO dto, Integer userId) throws BusinessRuleException {
		//checa para ver se o nome do topico não é nulo
		if (dto.getTitle()==null) throw new BusinessRuleException("o nome do Topico não pode ser nulo");
		//checa para ver se o nome do topico existe
		if(topicRepository.findByTitle(dto.getTitle())!=null)throw new BusinessRuleException("este nome de topico já existe "+ dto.getTitle());
		UserEntity user = userService.findById(userId);
		//cria a entity
		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.valueOf(StatusEnum.CREATING.name()))
				.title(dto.getTitle()).totalValue(BigDecimal.ZERO).user(user)
				.userId(user.getUserId()).build();
		//salva o topico
		entity = topicRepository.save(entity);
		//devolve o id
		log.info("#### CREATED USER #### ID -> '{}'", entity.getUserId());
		return entity.getTopicId();
	}
	
	public String updateFinancierTopic(Integer topicId, Boolean status) throws BusinessRuleException {
		//checa para ver se o topico existe
		TopicEntity topic = this.topicById(topicId);
		if (status) {
			//aprova o topico
			topic.setStatus(StatusEnum.CONCLUDED);
			topicRepository.save(topic);
			log.info("#### TOPIC #### ID -> '{}' : ALTERED STATUS TO -> '{}' ", topic.getTopicId(), topic.getStatus());
			return "Concluded topic";
		} else {
			//reprova o topico
			topic.setStatus(StatusEnum.FINANCIALLY_REPROVED);
			topicRepository.save(topic);
			log.info("#### TOPIC #### ID -> '{}' : ALTERED STATUS TO -> '{}' ", topic.getTopicId(), topic.getStatus());
			return "Topic financially reproved";
		}
	}

	public void openTopic(int idTopic, int userId) throws BusinessRuleException {
		//checa para ver se o topico existe
		TopicEntity findById = this.topicById(idTopic);
		//checa para ver se o topico é do usuario
		if(findById.getUserId()!=userId)throw new BusinessRuleException("voce não pode alterar um tópico que não é seu");
		//checa para ver se o topico esta com o status certo
		if(findById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("o topico não pode ser alterado pois já foi aberto");
		//checa para ver se o topico tem pelomenos um item
		if(findById.getItems().size()<1)throw new BusinessRuleException("o topico tem que ter pelo menos um item");
		//calcula o valor total dos items
		double sum = findById.getItems().stream().mapToDouble(item->item.getValue().doubleValue()).sum();
		findById.setTotalValue(new BigDecimal(sum));
		//troca o status para aberto
		findById.setStatus(StatusEnum.OPEN);
		//salva o topico
		TopicEntity save = this.topicRepository.save(findById);
		log.info("#### TOPIC #### ID -> '{}' : ALTERED STATUS TO -> '{}' ", save.getTopicId(), save.getStatus());
	}
	
	
	public Page<TopicDTO> getTopics(List<SimpleGrantedAuthority> authorities, int userId, int page, Integer topics, String title) throws BusinessRuleException {
		log.info("#### METHOD -> getTopics #### ID USER-> '{}'",userId);
		//transforma o SimpleGrantedAuthority em uma string
		List<String> collect = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
		//seta a pagina
		PageRequest pageRequest = PageRequest.of(
                page,
                ((topics==null)?4:topics),
                Sort.by(new Order(Direction.DESC, "status"), 
                	    new Order(Direction.ASC, "date")));
		//se o title for nulo inicia ele com uma string vazia
		//se tiver uma string procura um topico que contenha a string
		title = title==null?"":title;
		if(collect.contains("ROLE_MANAGER")) {
			//procura com o status aberto
			return this.topicRepository.findAllByStatus(StatusEnum.OPEN, title, pageRequest);
		}else if(collect.contains("ROLE_FINANCIER")) {
			//procura com o status aprovado pelo manager
			return this.topicRepository.findAllByStatus(StatusEnum.MANAGER_APPROVED, title, pageRequest);
		}else if(collect.contains("ROLE_BUYER")) {
			//procura por totdos menos os com status creating
			return this.topicRepository.findAllByStatusDifferent(StatusEnum.CREATING, title, pageRequest);
		}else {
			//procura todos os topico do user
			return topicRepository.findAllByUserId(userId, title, pageRequest);
		}
	}
	
	public TopicEntity topicById(Integer topicId) throws BusinessRuleException{
		return topicRepository.findById(topicId).orElseThrow((() -> new BusinessRuleException("topico não encontrado")));
	}
	
	public void save(TopicEntity topic) {
		//salva um topico
		this.topicRepository.save(topic);
	}

	public void deleteById(int topicId, int userId) throws BusinessRuleException {
		//checa se o topico existe
		TopicEntity topicById = this.topicById(topicId);
		//checa se o topico pertence ao user passado
		if(topicById.getUser().getUserId()!=userId)throw new BusinessRuleException("esse topico não pertence a esse user");
		//checa se o topico tem o status certo
		if(topicById.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("topico não pode ser deletado nesse status");
		//deleta o topico
		log.info("#### TOPIC #### ID -> '{}' : DELETED ", topicId);
		this.topicRepository.deleteById(topicId);
	}
}
