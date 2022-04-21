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
@Document(collection = "TOPIC")
public class TopicEntity {

    @Id
    private String topicId;

    private String userId;

    private String title;

    private LocalDate date;

    private Double totalValue;

    private String status;

    @DBRef
    private List<PurchaseListEntity> pucharses;
}
