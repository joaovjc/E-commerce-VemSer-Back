package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;


import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity(name = "topic")
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity {

    @Id
    @Column(name = "topic_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "topic_date")
    private LocalDate date;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "topicEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    @JsonIgnore
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuotationEntity> quotations;

//    private List<String> purchases;
//
//    private List<String> quotations;
}
