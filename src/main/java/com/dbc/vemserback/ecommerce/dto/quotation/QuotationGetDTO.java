package com.dbc.vemserback.ecommerce.dto.quotation;

import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class QuotationGetDTO {

    private Integer quotationId;

    private BigDecimal quotationPrice;

    private StatusEnum quotationStatus;

    private Integer userId;

    private Integer topicId;

}
