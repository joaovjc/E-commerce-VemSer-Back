package com.dbc.vemserback.ecommerce.dto.quotation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QuotationDTO extends QuotationCreateDTO{

    private Integer userId;
}
