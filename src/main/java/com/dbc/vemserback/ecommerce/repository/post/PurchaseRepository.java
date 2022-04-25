package com.dbc.vemserback.ecommerce.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
//    @Query("SELECT new com.dbc.vemserback.ecommerce.dto.Item.ItemDTO(p.itemName, p.description , p.value, p.file) FROM purchase p WHERE p.topicId = ?1")
//    List<ItemDTO> findAllByTopicId(Integer topicId);
	List<PurchaseEntity> findAllByTopicId(Integer topicId);
}
