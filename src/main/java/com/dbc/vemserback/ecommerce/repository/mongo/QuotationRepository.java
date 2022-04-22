package com.dbc.vemserback.ecommerce.repository.mongo;

import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends MongoRepository<QuotationEntity, String> {
}
