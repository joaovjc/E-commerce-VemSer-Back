package com.dbc.vemserback.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@Setter
@Document(collection = "ANNEX")
public class AnnexEntity {

    @Id
    private String annexId;

    private Integer listId;

    private String title;


}
