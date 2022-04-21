package com.dbc.vemserback.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "PUCHARSE_LIST")
public class PucharseListEntity {

    @Id
    @Column(name= "list_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listId;

    @Column(name = "topic_id", insertable = false, updatable = false)
    private Integer topicId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_topic")
    private LocalDate date;

    @Column(name = "total_value")
    private Double totalValue;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "topic_id", name = "topic_id")
    private TopicEntity topicEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "pucharseListEntity")
    private Set<AnnexEntity> annexEntities;
}
