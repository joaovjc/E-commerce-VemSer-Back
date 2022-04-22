package com.dbc.vemserback.ecommerce.repository.post;

import com.dbc.vemserback.ecommerce.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
