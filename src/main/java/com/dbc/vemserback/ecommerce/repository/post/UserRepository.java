package com.dbc.vemserback.ecommerce.repository.post;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbc.vemserback.ecommerce.dto.user.UserPageDTO;
import com.dbc.vemserback.ecommerce.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String email);
	
	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.user.UserPageDTO(u.userId, u.fullName, u.email, u.groupEntity.name, u.profileImage) FROM USER_COMMERCE u WHERE lower(u.fullName) like lower(concat('%', ?1 , '%'))")
	Page<UserPageDTO> getUserByFullName(String fullName, Pageable pageable);
}
