package com.dbc.vemserback.ecommerce.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "pictures")
public class PictureEntity {

    @Id
    private String pictureId;

    private byte[] picture;

    private Integer userId;



}
