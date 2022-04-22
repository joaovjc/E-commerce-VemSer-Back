package com.dbc.vemserback.ecommerce.entity;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "quotation")
public class QuotationEntity {

    @Id
    private String quotationId;

    private String topicId;

    private Integer userId;

    private double quotationPrice;

    private StatusEnum quotationStatus;

    
}
