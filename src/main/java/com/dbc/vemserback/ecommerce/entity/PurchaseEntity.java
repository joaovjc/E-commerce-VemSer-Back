package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document
public class PurchaseEntity {

    @Id
    private String listId;

    private String name;

    private BigDecimal totalValue;
    
    private String fileName;
    
    private byte[] file;
}
