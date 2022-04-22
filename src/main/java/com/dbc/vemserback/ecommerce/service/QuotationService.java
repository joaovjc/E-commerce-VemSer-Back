package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationCreateDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.repository.mongo.QuotationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final ObjectMapper objectMapper;


    public List<QuotationDTO> quotationList() {
        return quotationRepository.findAll().stream().map(quotation -> objectMapper.convertValue(quotation, QuotationDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    public QuotationDTO createQuotation(QuotationCreateDTO quotationCreateDTO) {
        QuotationEntity quotationEntity = objectMapper.convertValue(quotationCreateDTO, QuotationEntity.class);
        QuotationEntity savedQuotationEntity = quotationRepository.save(quotationEntity);
        return objectMapper.convertValue(savedQuotationEntity, QuotationDTO.class);
    }
}
