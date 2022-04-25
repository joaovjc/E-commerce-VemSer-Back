package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class PurchaseGetDTO {


    private Integer purchaseId;

    private String itemName;

    private String description;

    private BigDecimal value;

    private String fileName;

    private byte[] file;

    private Integer topicId;
}
