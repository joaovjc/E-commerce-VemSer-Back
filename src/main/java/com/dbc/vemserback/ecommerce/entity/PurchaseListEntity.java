package com.dbc.vemserback.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Document(collection = "PUCHARSE_LIST")
public class PurchaseListEntity {

    @Id
    private String listId;

    private Integer topicId;

    private String name;

    private LocalDate date;

    private Double totalValue;

    @DBRef
    private TopicEntity topicEntity;

    @DBRef
    private List<AnnexEntity> annexEntities;
}
