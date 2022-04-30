package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<ItemEntity> items;

    @JsonIgnore
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuotationEntity> quotations;
}
