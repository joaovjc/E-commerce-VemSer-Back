package com.dbc.vemserback.ecommerce.repository.mongo;

import com.dbc.vemserback.ecommerce.entity.PurchaseListEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseListRepository extends MongoRepository<PurchaseListEntity, String> {


}
