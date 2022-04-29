package com.dbc.vemserback.ecommerce.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.topic.TopicDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
	
	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.topic.TopicDTO(t.topicId, t.title, t.date, t.totalValue, t.status) FROM topic t WHERE t.userId = ?1 AND lower(t.title) like lower(concat('%', ?2 , '%'))")
	Page<TopicDTO> findAllByUserId(int idUser, String title, PageRequest pageRequest);
	
	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.topic.TopicDTO(t.topicId, t.title, t.date, t.totalValue, t.status) FROM topic t WHERE t.status = ?1 AND lower(t.title) like lower(concat('%', ?2 , '%'))")
	Page<TopicDTO> findAllByStatus(StatusEnum enumTopic, String title, PageRequest pageRequest);

	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.topic.TopicDTO(t.topicId, t.title, t.date, t.totalValue, t.status) FROM topic t WHERE t.status <> ?1 AND lower(t.title) like lower(concat('%', ?2 , '%'))")
	Page<TopicDTO> findAllByStatusDifferent(StatusEnum enumTopic, String title, PageRequest pageRequest);

	TopicEntity findByTitle(String title);

}
