package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.QuotationRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.custom.TopicrepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final TopicrepositoryImpl topicrepositoryImpl;
    private final QuotationRepository quotationRepository;
    private final TopicService topicService;
    private final ObjectMapper objectMapper;
    private final UserService userService;


    public List<QuotationDTO> quotationList() {
        return quotationRepository.findAll().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    public String createQuotation(QuotationCreateDTO quotationCreateDTO) throws BusinessRuleException {

        TopicEntity topicEntity = topicService.topicById(quotationCreateDTO.getTopicId());

        UserEntity userEntity = userService.findById(userService.getLogedUserId());

        QuotationEntity build = QuotationEntity.builder()
                .quotationPrice(quotationCreateDTO.getQuotationPrice())
                .userId(userEntity.getUserId())
                .quotationStatus(StatusEnum.OPEN)
                .topicId(topicEntity.getTopicId())
                .topic(topicEntity)
                .userId(userEntity.getUserId())
                .userEntity(userEntity)
                .build();

        quotationRepository.save(build);

        return "Quotation created";
    }

    public QuotationDTO aproveQuotation(Integer topicId, Integer idQuotation) throws BusinessRuleException {
        if(quotationsByIdTopic(topicId))
        {
            QuotationEntity quotationEntity = quotationRepository.findById(idQuotation).orElseThrow((() -> new BusinessRuleException("Quotation not found")));
            quotationEntity.setQuotationStatus(StatusEnum.MANAGER_APPROVED);
            topicService.updateStatusToTopic(topicId, StatusEnum.MANAGER_APPROVED);
            return objectMapper.convertValue(quotationRepository.save(quotationEntity), QuotationDTO.class);
        }
        return null;
    }


    public void reproveAllQuotations(Integer topicId) throws BusinessRuleException {
        if(quotationsByIdTopic(topicId)) {
            List<QuotationEntity> quotationEntities = quotationRepository.findAllByTopicId(topicId);
            quotationEntities.forEach(quotationEntity -> {
                quotationEntity.setQuotationStatus(StatusEnum.MANAGER_REPROVED);
            });
            quotationRepository.saveAll(quotationEntities);
            topicService.updateStatusToTopic(topicId, StatusEnum.MANAGER_REPROVED);
        }
    }

    public Boolean quotationsByIdTopic(Integer topicId) throws BusinessRuleException {
        TopicEntity topic = topicService.topicById(topicId);
        return topic.getQuotations().size() >= 2;
    }

    public List<QuotationByTopicDTO> quotationsByTopic(Integer topicId) throws BusinessRuleException {
        TopicEntity topic = topicService.topicById(topicId);
        return topic.getQuotations().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationByTopicDTO.class)).collect(Collectors.toList());
    }


}
