package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import java.math.BigDecimal;

import lombok.Data;

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
