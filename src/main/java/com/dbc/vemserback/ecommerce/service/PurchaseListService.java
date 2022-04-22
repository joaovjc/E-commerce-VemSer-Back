package com.dbc.vemserback.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseListEntity;
import com.dbc.vemserback.ecommerce.repository.mongo.PurchaseListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseListService {
    @Autowired
    private final PurchaseListRepository purchaseListRepository;
    @Autowired
    private final ObjectMapper objectMapper;

    public PurchaseDTO createPurchaseList(PurchaseDTO purchaseListCreateDTO) {

        PurchaseListEntity purchaseListEntity = purchaseListRepository.save(objectMapper.convertValue(purchaseListCreateDTO, PurchaseListEntity.class));

//        return objectMapper.convertValue(purchaseListEntity, PurchaseListDTO.class);
        return null;
    }

//    public List<PurchaseListCreateDTO> listTopics() {
//        return purchaseListRepository.findAll().stream().map(purchaseList -> objectMapper.convertValue(purchaseList, PurchaseListCreateDTO.class)).collect(Collectors.toList());
//    }



}
