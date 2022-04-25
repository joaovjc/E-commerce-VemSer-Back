package com.dbc.vemserback.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.user.PictureDTO;
import com.dbc.vemserback.ecommerce.entity.PictureEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.mongo.PictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {
	private final PictureRepository pictureRepository;
	private final FileService fileService;
	
	public PictureDTO create(MultipartFile multipartFile, int userId) throws BusinessRuleException {
		PictureEntity entity = new PictureEntity();
		entity.setPicture(multipartFile!=null?fileService.convertToByte(multipartFile):null);
		entity.setUserId(userId);
		PictureEntity save = pictureRepository.save(entity);
		return new PictureDTO(save.getPicture(),save.getUserId());
	}
	
	public PictureEntity findByUserId(int userId) {
		return this.pictureRepository.findByUserId(userId).orElse(null);
	}
	
	
}
