package com.dbc.vemserback.ecommerce.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.entity.QuotationEntity;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Integer> {
    List<QuotationEntity> findAllByTopicId(Integer topicId);
}
