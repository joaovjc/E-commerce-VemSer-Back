package com.dbc.vemserback.ecommerce.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "pictures")
public class PictureEntity {

    @Id
    private String pictureId;

    private byte[] picture;

    private Integer userId;

}
