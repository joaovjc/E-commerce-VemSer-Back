package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.entity.PictureEntity;
import com.dbc.vemserback.ecommerce.repository.PictureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {
	private final PictureRepository pictureRepository;
	
	//TODO tratamento de erros
	public String create(MultipartFile multipartFile, int userId) {

		PictureEntity entity = new PictureEntity();
		entity.setUserId(userId);
		File convertToFile = null;

		try {
			convertToFile = this.convertToFile(multipartFile, multipartFile.getOriginalFilename());
			entity.setPicture(Files.readAllBytes(convertToFile.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PictureEntity save = pictureRepository.save(entity);
		convertToFile.delete();
		return save.getPictureId();
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
		return this.pictureRepository.findByUserId(userId).orElseThrow();
	}

}
