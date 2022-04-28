package com.dbc.vemserback.ecommerce.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @Column(name = "item_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

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
