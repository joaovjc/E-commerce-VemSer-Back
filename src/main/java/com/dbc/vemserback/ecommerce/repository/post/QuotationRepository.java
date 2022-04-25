package com.dbc.vemserback.ecommerce.repository.post;

import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Integer> {
    List<QuotationEntity> findAllByTopicId(Integer topicId);
}
