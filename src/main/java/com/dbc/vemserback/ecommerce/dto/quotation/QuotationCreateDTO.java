package com.dbc.vemserback.ecommerce.dto.quotation;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class QuotationCreateDTO {

    private Integer topicId;

    private BigDecimal quotationPrice;

}
