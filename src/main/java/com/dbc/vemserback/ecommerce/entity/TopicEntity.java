package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TOPIC")
public class TopicEntity {

    @Id
    private String topicId;

    private Integer userId;

    private String title;

    private LocalDate date;

    private BigDecimal totalValue;

    private StatusEnum status;

    @DBRef
    private List<PurchaseListEntity> pucharses;
}
