package com.dbc.vemserback.ecommerce.dto.PurchaseList;

import com.dbc.vemserback.ecommerce.entity.AnnexEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

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
