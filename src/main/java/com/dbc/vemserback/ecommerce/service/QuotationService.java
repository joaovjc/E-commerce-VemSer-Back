package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
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
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final TopicService topicService;
    private final ObjectMapper objectMapper;
    private final UserService userService;


    public List<QuotationDTO> quotationList() {
        return quotationRepository.findAll().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    public QuotationEntity createQuotation(QuotationCreateDTO quotationCreateDTO, int userId) throws BusinessRuleException {

        TopicEntity topicEntity = topicService.topicById(quotationCreateDTO.getTopicId());

        UserEntity userEntity = userService.findById(userId);

        QuotationEntity build = QuotationEntity.builder()
                .quotationPrice(quotationCreateDTO.getQuotationPrice())
                .quotationStatus(StatusEnum.OPEN)
                .topic(topicEntity)
                .userEntity(userEntity)
                .build();

        QuotationEntity save = quotationRepository.save(build);
        
        save.setTopicId(quotationCreateDTO.getTopicId());
        save.setUserId(userId);
        
        return save;
    }

    //Manager
    public QuotationDTO aproveQuotation(Integer idQuotation) throws BusinessRuleException {
        QuotationEntity quotationEntity = findQuotationById(idQuotation);
        if (quotationsByIdTopic(quotationEntity.getTopicId())){
            quotationEntity.setQuotationStatus(StatusEnum.MANAGER_APPROVED);
            topicService.updateStatusToTopic(quotationEntity.getTopicId(), StatusEnum.MANAGER_APPROVED);
            return objectMapper.convertValue(quotationRepository.save(quotationEntity), QuotationDTO.class);
        }
        return null;
    }

    //Manager
    public void reproveAllQuotations(Integer topicId) throws BusinessRuleException {
        if (quotationsByIdTopic(topicId)) {
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
        if(topic.getStatus()!=StatusEnum.OPEN) throw new BusinessRuleException("Topic not open");
        return topic.getQuotations().size() >= 2;
    }

    public List<QuotationByTopicDTO> quotationsByTopic(Integer topicId) throws BusinessRuleException {
        TopicEntity topic = topicService.topicById(topicId);
        return topic.getQuotations().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationByTopicDTO.class)).collect(Collectors.toList());
    }

    private QuotationEntity findQuotationById(Integer quotationId) throws BusinessRuleException {
        return quotationRepository.findById(quotationId).orElseThrow((() -> new BusinessRuleException("Quotation not found")));
    }

}
