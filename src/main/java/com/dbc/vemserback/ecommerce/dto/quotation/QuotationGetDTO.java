package com.dbc.vemserback.ecommerce.dto.quotation;

import java.math.BigDecimal;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;

import lombok.Data;

@Data
public class QuotationGetDTO {

    private Integer quotationId;

    private BigDecimal quotationPrice;

    private StatusEnum quotationStatus;

    private Integer userId;

    private Integer topicId;

}
