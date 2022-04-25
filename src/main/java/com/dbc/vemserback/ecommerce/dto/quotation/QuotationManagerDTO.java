package com.dbc.vemserback.ecommerce.dto.quotation;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotationManagerDTO {

    private Integer topicId;
    private StatusEnum quotationStatus;
}
