package com.dbc.vemserback.ecommerce.repository.mongo.custom;

import org.springframework.stereotype.Component;

@Component
public interface CustomTopicRepository {
	
	boolean updateAndAddItem(String topicId, String entityId);
	
}
