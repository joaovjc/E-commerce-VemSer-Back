package com.dbc.vemserback.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "ANNEX")
public class AnnexEntity {

    @Id
    @Column(name = "annex_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer annexId;

    @Column(name = "list_id", insertable = false, updatable = false)
    private Integer listId;

    @Column(name = "title")
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "list_id", name = "list_id")
    private PucharseListEntity pucharseListEntity;

}
