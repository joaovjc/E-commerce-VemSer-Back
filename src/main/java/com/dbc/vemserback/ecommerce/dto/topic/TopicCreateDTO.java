package com.dbc.vemserback.ecommerce.dto.topic;

import java.time.LocalDate;
import java.util.List;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;

import lombok.Data;

@Data
public class TopicCreateDTO {

    private Integer userId;

    private String title;

    private LocalDate date;

    private Double totalValue;

    private String status;

    List<PurchaseDTO> purchases;
}
