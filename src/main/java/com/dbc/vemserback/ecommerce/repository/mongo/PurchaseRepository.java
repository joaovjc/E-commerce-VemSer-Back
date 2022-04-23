package com.dbc.vemserback.ecommerce.repository.mongo;

import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRepository extends MongoRepository<PurchaseEntity, String> {


}
