package com.dbc.vemserback.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.annotation.Documented;

@Getter
@Setter

public class AnnexEntity {

    @Id
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
