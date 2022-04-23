package com.dbc.vemserback.ecommerce.repository.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;

@Repository
public interface TopicRepository extends MongoRepository<TopicEntity, String> {

	@Aggregation(pipeline = { 
			"{ '$match' : {'userId': ?0} }", 
			"{'$sort':{'date':-1}}" 
	})
	@Query(fields = "{topicId : 1, title : 1, date : 1, totalValue : 1, status : 1}")
	List<TopicAgreg> findAllTopicsByIdUser(int idUser);
//	Page<TopicAgreg> findAllTopicsByIdUser(int idUser, Pageable pageable);
	
	@Query(fields = "{purchases : 1}")
	List<String> findAllPurchasesIdByIdTopicAndIdUser(String idTopic, int idUser);
}
