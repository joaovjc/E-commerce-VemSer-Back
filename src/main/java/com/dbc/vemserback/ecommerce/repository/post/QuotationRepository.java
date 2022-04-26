package com.dbc.vemserback.ecommerce.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO;
import com.dbc.vemserback.ecommerce.entity.QuotationEntity;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Integer> {
    List<QuotationEntity> findAllByTopicId(Integer topicId);
    
    @Query("SELECT new com.dbc.vemserback.ecommerce.dto.quotation.QuotationByTopicDTO(q.quotationId, q.topicId, q.quotationStatus, q.quotationPrice) FROM quotation q WHERE q.topicId = ?1 AND q.quotationStatus = 'MANAGER_APPROVED'")
	List<QuotationByTopicDTO> findAllByTopicIdWhereStatusApproved(Integer topicId);
}
