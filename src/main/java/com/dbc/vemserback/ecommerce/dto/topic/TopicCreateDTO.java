package com.dbc.vemserback.ecommerce.dto.topic;

import com.dbc.vemserback.ecommerce.entity.PucharseListEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TopicCreateDTO {

    private Integer userId;

    private String title;

    private LocalDate date;

    private Double totalValue;

    private String status;

    private List<PucharseListEntity> pucharses;
}
