package com.dbc.vemserback.ecommerce.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.topic.TopicAgreg(t.topicId, t.title, t.date, t.totalValue, t.status) FROM topic t WHERE t.userId = ?1")
	Page<TopicAgreg> findAllByUserId(Integer userId, Pageable pageable);


}
