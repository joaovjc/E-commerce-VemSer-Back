package com.dbc.vemserback.ecommerce.dto;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuotationManagerDTO {

    private String topicId;
    private StatusEnum quotationStatus;
}
