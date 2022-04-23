package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationManagerDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.mongo.QuotationRepository;
import com.dbc.vemserback.ecommerce.repository.mongo.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;


    public List<QuotationDTO> quotationList() {
        return quotationRepository.findAll().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    public QuotationDTO createQuotation(QuotationCreateDTO quotationCreateDTO) {
        QuotationEntity quotationEntity = objectMapper.convertValue(quotationCreateDTO, QuotationEntity.class);
        quotationEntity.setUserId(userService.getLogedUserId());
        QuotationEntity savedQuotationEntity = quotationRepository.save(quotationEntity);
        return objectMapper.convertValue(savedQuotationEntity, QuotationDTO.class);
    }

    public QuotationDTO updateManagerQuotation(QuotationManagerDTO quotationManagerDTO) throws BusinessRuleException {
        if(quotationsByIdTopic(quotationManagerDTO.getTopicId()))
        {
            QuotationEntity quotationEntity = quotationRepository.findById(quotationManagerDTO.getQuotationId())
                    .orElseThrow(()->new BusinessRuleException("Not found"));
            quotationEntity.setQuotationStatus(quotationManagerDTO.getQuotationStatus());
            QuotationEntity savedQuotationEntity = quotationRepository.save(quotationEntity);
                return objectMapper.convertValue(savedQuotationEntity, QuotationDTO.class);
        }
        return null;
    }


    public Boolean quotationsByIdTopic(String topicId){
        return topicRepository.findById(topicId).get().getQuatations().size()>=2;
    }


}
