package com.dbc.vemserback.ecommerce.dto.quotation;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class QuotationByTopicDTO {

    private Integer quotationId;
    private Integer topicId;
    @Enumerated(EnumType.STRING)
    private StatusEnum quotationStatus;
    private BigDecimal quotationPrice;
}
