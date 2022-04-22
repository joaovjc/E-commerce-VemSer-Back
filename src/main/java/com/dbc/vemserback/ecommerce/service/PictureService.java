package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PictureDTO;
import com.dbc.vemserback.ecommerce.entity.PictureEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.PictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {
	private final PictureRepository pictureRepository;
	
	public PictureDTO create(MultipartFile multipartFile, int userId) throws BusinessRuleException {
		File convertToFile = null;
		byte[] readAllBytes = null;

		try {
			convertToFile = this.convertToFile(multipartFile, multipartFile.getOriginalFilename());
			readAllBytes = Files.readAllBytes(convertToFile.toPath());
		} catch (IOException e) {
			throw new BusinessRuleException("Problems to parse the file: "+ e.getCause());
		}
		PictureEntity entity = new PictureEntity();
		entity.setPicture(readAllBytes);
		entity.setUserId(userId);
		PictureEntity save = pictureRepository.save(entity);
		convertToFile.delete();
		return new PictureDTO(save.getPicture(),save.getUserId());
	}

	private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}
	
	public PictureEntity findByUserId(int userId) {
		return this.pictureRepository.findByUserId(userId).orElse(null);
	}
	
	
}
