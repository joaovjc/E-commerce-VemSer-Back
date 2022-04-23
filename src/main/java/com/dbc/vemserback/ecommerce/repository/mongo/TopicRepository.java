package com.dbc.vemserback.ecommerce.repository.mongo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;

@Repository
public interface TopicRepository extends MongoRepository<TopicEntity, String> {

	@Query(fields = "{_id : 1 , title : 1, date : 1, totalValue : 1, status : 1}")
	Slice<TopicAgreg> findAllTopicsByUserId(int userId, PageRequest pageRequest);
//	Page<TopicAgreg> findAllTopicsByIdUser(int idUser, Pageable pageable);
	
	@Aggregation(pipeline = { 
			"{ '$match' : {'$and' : [{'_id':?0}, {'userId':?1}] } }"
	})
	@Query(fields = "{purchases : 1}")
	List<String> findAllPurchasesByIdAndIdUser(String idTopic, int idUser);
}
