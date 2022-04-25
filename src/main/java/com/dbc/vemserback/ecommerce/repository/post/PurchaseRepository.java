package com.dbc.vemserback.ecommerce.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseAgreg;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
	
//	@Aggregation(pipeline = {
//			"{'$match': {'$expr': {'_id': ?0} } }"
//	})
//	@Query(fields = "{name : 1, totalValue : 1, valor : 1, file : 1}")
//	List<PurchaseAgreg> findAllById(List<String> ids);

    @Query("SELECT new com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseAgreg(p.itemName, p.description , p.value, p.file) FROM purchase p WHERE p.topicId = ?1")
    List<PurchaseAgreg> findAllByTopicId(Integer topicId);
}
