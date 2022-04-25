package com.dbc.vemserback.ecommerce.dto.topic;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseGetDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationGetDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FullTopicDTO {

    private Integer topicId;

    private Integer userId;

    private String title;

    private LocalDate date;

    private BigDecimal totalValue;

    private StatusEnum status;

    private List<PurchaseGetDTO> purchases;

    private List<QuotationGetDTO> quotations;

}
