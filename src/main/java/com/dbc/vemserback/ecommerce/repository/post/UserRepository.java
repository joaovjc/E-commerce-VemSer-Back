package com.dbc.vemserback.ecommerce.repository.post;

import com.dbc.vemserback.ecommerce.dto.user.UserPageDTO;
import com.dbc.vemserback.ecommerce.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String email);

	@Query("SELECT new com.dbc.vemserback.ecommerce.dto.user.UserPageDTO(u.userId, u.fullName, u.email, u.groupEntity.name, u.profileImage) FROM USER_COMMERCE u")
	Page<UserPageDTO> findAllOrOrderByFullName(Pageable pageable);

	List<UserEntity> getUserByFullName(String fullName);
}
