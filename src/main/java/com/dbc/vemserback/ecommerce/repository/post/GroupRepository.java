package com.dbc.vemserback.ecommerce.repository.post;

import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
}
