package com.dbc.vemserback.ecommerce.dto.topic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseGetDTO;
import com.dbc.vemserback.ecommerce.dto.quotation.QuotationGetDTO;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;

import lombok.Data;

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
