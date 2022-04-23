package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "topic")
public class TopicEntity {

    @Id
    private String topicId;

    private Integer userId;

    private String title;

    private LocalDate date;

    private BigDecimal totalValue;

    private StatusEnum status;

    @DBRef
    private List<PurchaseEntity> pucharses;

    private List<QuotationEntity> quatations;
}
