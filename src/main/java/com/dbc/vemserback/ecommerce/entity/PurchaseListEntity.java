package com.dbc.vemserback.ecommerce.entity;

import java.time.LocalDate;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "PUCHARSE_LIST")
public class PurchaseListEntity {

    @Id
    private String listId;

    private Integer topicId;

    private String name;

    private LocalDate date;

    private Double totalValue;
    
    private String fileName;
    
    private Byte[] file;
}
