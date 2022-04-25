package com.dbc.vemserback.ecommerce.dto.topic;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TopicDTO {
	@Id
	private Integer topicId;
	private String title;
	private LocalDate date;
	private BigDecimal totalValue;
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
}
