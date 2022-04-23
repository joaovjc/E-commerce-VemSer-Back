package com.dbc.vemserback.ecommerce.entity;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@Document
public class QuotationEntity {

    @Id
    private String quotationId;

    private String topicId;

    private Integer userId;

    private double quotationPrice;

    private StatusEnum quotationStatus;

    
}
