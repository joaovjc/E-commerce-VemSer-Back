package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity(name = "purchase")
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntity {

    @Id
    @Column(name = "purchase_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file")
    private byte[] file;

    @Column(name = "topic_id", insertable = false, updatable = false)
    private Integer topicId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    private TopicEntity topicEntity;
}
