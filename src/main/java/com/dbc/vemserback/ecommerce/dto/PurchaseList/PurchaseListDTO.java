package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import java.time.LocalDate;
import java.util.List;

import com.dbc.vemserback.ecommerce.entity.AnnexEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseListDTO {
    private String listId;

    private Integer topicId;

    private String name;

    private LocalDate date;

    private Double totalValue;

    private List<AnnexEntity> annexEntities;
}
