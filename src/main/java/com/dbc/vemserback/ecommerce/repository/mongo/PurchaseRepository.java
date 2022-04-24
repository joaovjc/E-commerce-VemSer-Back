package com.dbc.vemserback.ecommerce.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseAgreg;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;

public interface PurchaseRepository extends MongoRepository<PurchaseEntity, String> {
	
	@Aggregation(pipeline = {
			"{'$match': {'$expr': {'_id': ?0} } }"
	})
	@Query(fields = "{name : 1, totalValue : 1, valor : 1, file : 1}")
	List<PurchaseAgreg> findAllById(List<String> ids);
}
