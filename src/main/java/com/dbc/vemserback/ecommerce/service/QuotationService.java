package com.dbc.vemserback.ecommerce.service;

import static com.dbc.vemserback.ecommerce.enums.StatusEnum.MANAGER_APPROVED;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.QuotationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuotationService {

	private final QuotationRepository quotationRepository;
	private final TopicService topicService;
	private final ObjectMapper objectMapper;
	private final UserService userService;

	public boolean createQuotation(Integer topicId, Double preco, int userId) throws BusinessRuleException {
		log.info("Criando cotação");
		// checa se o topico existe
		TopicEntity topicEntity = topicService.topicById(topicId);
		// checa se o status do topico permite adicionar cotações
		if (topicEntity.getStatus() != StatusEnum.OPEN)
			throw new BusinessRuleException("o topico ainda não está aberto para cotações");
		// busca o usuario
		UserEntity userEntity = userService.findById(userId);
		// converte para entity
		QuotationEntity build = QuotationEntity.builder().quotationPrice(new BigDecimal(preco))
				.quotationStatus(StatusEnum.OPEN).topicId(topicId).userId(userId).topic(topicEntity)
				.userEntity(userEntity).build();
		// salva a entity
		quotationRepository.save(build);
		return true;
	}

	public void managerAproveOrReproveTopic(Integer topicId, Integer quotationId, Boolean flag)
			throws BusinessRuleException {
		log.info("Aprovando ou reprovando uma cotação");
		// checa se o topico existe
		TopicEntity topic = topicService.topicById(topicId);
		// checa se o topico esta com o status certo para ser aprovado
		if (topic.getStatus() != StatusEnum.OPEN)
			throw new BusinessRuleException("o Topico não foi aberto");
		// checa se o topico tem ao menos 2 cotações
		if (topic.getQuotations().size() < 2)
			throw new BusinessRuleException("o topico não tem duas cotações");
		List<QuotationEntity> quotationEntities = topic.getQuotations();

		// seta todas as quotations para reproved
		quotationEntities.forEach(quotationEntity -> {
			quotationEntity.setQuotationStatus(StatusEnum.MANAGER_REPROVED);
		});
		
		//se for para aprovar a cotação a flag sera marcada como true
		if (flag) {
			//então sera atualizada somente a cotação do id passado para aproved
			quotationEntities.stream()
			.filter(quotationEntity -> quotationEntity.getQuotationId().equals(quotationId))
			.findFirst().orElseThrow((() -> new BusinessRuleException("Cotação não encontrada")))
			.setQuotationStatus(MANAGER_APPROVED);
			//e por fim o topico será marcado como manager approved
			topic.setStatus(MANAGER_APPROVED);
		} else {
			//se a lfag for false o topico é reprovado
			topic.setStatus(StatusEnum.MANAGER_REPROVED);
		}
		topic.setQuotations(quotationEntities);
		//topico é salvo
		this.topicService.save(topic);
	}
	
	public List<QuotationByTopicDTO> quotationsByTopic(Integer topicId, List<SimpleGrantedAuthority> authorities)
			throws BusinessRuleException {
		log.info("Listando cotações por topico");
		// checa se o topico existe
		TopicEntity topic = topicService.topicById(topicId);
		//checa se tem a role certa para procurar somente a com a cotação aprovado pelo manager
		List<String> collect = authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
		if (collect.contains("ROLE_FINANCIER")) {
			return this.quotationRepository.findAllByTopicIdWhereStatusApproved(topicId);
		} else {
			//se for qualquer outro usuario devolve todas as cotações
			return topic.getQuotations().stream()
					.map(quotation -> objectMapper.convertValue(quotation, QuotationByTopicDTO.class))
					.collect(Collectors.toList());
		}
	}

	public QuotationEntity findQuotationById(Integer quotationId) throws BusinessRuleException {
		log.info("Procurando uma cotação por id");
		return quotationRepository.findById(quotationId)
				.orElseThrow((() -> new BusinessRuleException("Cotação não encontrada")));
	}

}
