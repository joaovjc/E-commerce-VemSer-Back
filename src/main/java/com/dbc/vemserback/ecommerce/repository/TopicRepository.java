package com.dbc.vemserback.ecommerce.repository;

import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends MongoRepository<TopicEntity, String> {
}
