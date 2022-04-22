package com.dbc.vemserback.ecommerce.repository.mongo;

import com.dbc.vemserback.ecommerce.entity.PictureEntity;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends MongoRepository<PictureEntity, String> {
	Optional<PictureEntity> findByUserId(int userId);
}
