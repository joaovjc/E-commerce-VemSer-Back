package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationManagerDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.mongo.QuotationRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.custom.TopicrepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean createQuotation(QuotationCreateDTO quotationCreateDTO) {

        QuotationEntity build = QuotationEntity.builder()
                .quotationPrice(quotationCreateDTO.getQuotationPrice())
                .userId(userService.getLogedUserId())
                .build();

        QuotationEntity save = quotationRepository.save(build);

        return topicrepositoryImpl.updateAndAddQuotation(quotationCreateDTO.getTopicId(), save.getQuotationId());
    }

//    public QuotationDTO createQuotation(QuotationCreateDTO quotationCreateDTO) {
//        QuotationEntity quotationEntity = objectMapper.convertValue(quotationCreateDTO, QuotationEntity.class);
//        quotationEntity.setUserId(userService.getLogedUserId());
//        quotationEntity.setQuotationStatus(StatusEnum.OPEN);
//        QuotationEntity savedQuotationEntity = quotationRepository.save(quotationEntity);
//        return objectMapper.convertValue(savedQuotationEntity, QuotationDTO.class);
//    }

    public QuotationDTO aproveManagerQuotation(String topicId, String idQuotation) {
        if(quotationsByIdTopic(topicId))
        {
            QuotationEntity quotationEntity = quotationRepository.findById(idQuotation).orElseThrow();
            quotationEntity.setQuotationStatus(StatusEnum.MANAGER_APPROVED);

            return objectMapper.convertValue(quotationRepository.save(quotationEntity), QuotationDTO.class);
        }
        return null;
    }


    public Boolean quotationsByIdTopic(String topicId){
        TopicEntity topic= topicService.topicById(topicId);
        return topic.getQuotations().size() >= 2;
    }


}
