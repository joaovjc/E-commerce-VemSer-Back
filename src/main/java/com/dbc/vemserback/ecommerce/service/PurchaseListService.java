package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseListCreateDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseListDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseListEntity;
import com.dbc.vemserback.ecommerce.repository.PurchaseListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseListService {
    @Autowired
    private final PurchaseListRepository purchaseListRepository;
    @Autowired
    private final ObjectMapper objectMapper;

    public PurchaseListDTO createPurchaseList(PurchaseListCreateDTO purchaseListCreateDTO) {

        PurchaseListEntity purchaseListEntity = purchaseListRepository.save(objectMapper.convertValue(purchaseListCreateDTO, PurchaseListEntity.class));

        return objectMapper.convertValue(purchaseListEntity, PurchaseListDTO.class);
    }

    public List<PurchaseListCreateDTO> listTopics() {
        return purchaseListRepository.findAll().stream().map(purchaseList -> objectMapper.convertValue(purchaseList, PurchaseListCreateDTO.class)).collect(Collectors.toList());
    }



}
