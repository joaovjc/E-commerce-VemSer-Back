package com.dbc.vemserback.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity(name = "TOPIC")
public class TopicEntity {
    @Id
    @Column(name= "topic_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "date_topic")
    private LocalDate date;

    @Column(name = "total_value")
    private Double totalValue;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private UserEntity userEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "topicEntity")
    private Set<PucharseListEntity> pucharses;
}
