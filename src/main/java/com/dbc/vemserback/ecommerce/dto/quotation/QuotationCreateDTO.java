package com.dbc.vemserback.ecommerce.dto.quotation;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import lombok.Data;

@Data
public class QuotationCreateDTO {

    private String topicId;

    private double quotationPrice;

}
