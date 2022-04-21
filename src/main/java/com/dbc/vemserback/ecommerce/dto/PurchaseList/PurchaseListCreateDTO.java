package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import com.dbc.vemserback.ecommerce.entity.AnnexEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PurchaseListCreateDTO {

    private String name;

    private LocalDate date;

    private Double totalValue;

    private TopicEntity topicEntity;

    private List<AnnexEntity> annexEntities;
}
