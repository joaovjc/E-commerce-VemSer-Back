package com.dbc.vemserback.ecommerce.repository.mongo.custom;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.mongodb.client.result.UpdateResult;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TopicrepositoryImpl implements CustomTopicRepository{
	private final MongoTemplate mongoTemplate;
	
	@Override
	public boolean updateAndAddItem(String topicId, String entityId) {
		Query query = new Query(Criteria.where("_id").is(topicId));
        Update update = new Update();
        update.addToSet("purchases", entityId);
        UpdateResult updateFirst = mongoTemplate.updateFirst(query, update, TopicEntity.class);
		
		return updateFirst.wasAcknowledged();
	}
}
