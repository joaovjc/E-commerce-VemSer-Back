package com.dbc.vemserback.ecommerce.entity;

import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity(name = "quotation")
@NoArgsConstructor
@AllArgsConstructor
public class QuotationEntity {

    @Id
    @Column(name = "quotation_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quotationId;

    @Column(name = "quotation_price")
    private BigDecimal quotationPrice;

    @Column(name = "quotation_status")
    @Enumerated(EnumType.STRING)
    private StatusEnum quotationStatus;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "topic_id", insertable = false, updatable = false)
    private Integer topicId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    private TopicEntity topic;
    
}
