package com.dbc.vemserback.ecommerce.repository;

import com.dbc.vemserback.ecommerce.entity.AnnexEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexRepository extends MongoRepository<AnnexEntity, String> {
}
