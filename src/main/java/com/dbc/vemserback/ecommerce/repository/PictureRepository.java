package com.dbc.vemserback.ecommerce.repository;

import com.dbc.vemserback.ecommerce.entity.PictureEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends MongoRepository<PictureEntity, String> {
}
