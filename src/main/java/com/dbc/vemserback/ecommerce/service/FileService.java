package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.FileRepository;
import com.google.cloud.storage.Blob;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileService {
	private final FileRepository fileRepository;

	private String uploadFile(File file, String fileName, String contentType) throws IOException {
		fileRepository.save(file, fileName, contentType);
		return String.format(fileName);
	}

	private File convertToFile(MultipartFile multipartFile, String fileName) {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempFile;
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public String uploadImage(MultipartFile multipartFile) throws BusinessRuleException {
		String fileName = multipartFile.getOriginalFilename();
		fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

		File file = this.convertToFile(multipartFile, fileName);
		String nome = null;	
		try {
			nome = this.uploadFile(file, fileName, "media");
		} catch (IOException e) {
			log.info("erro -> '{}' ", e.getCause());
			throw new BusinessRuleException("Faild to send File");
		}
		file.delete();
		return nome;
	}

	public String findImageByName(String fileName) throws BusinessRuleException {
		Blob blob = this.fileRepository.findByFileName(fileName)
				.orElseThrow(() -> new BusinessRuleException("The Image does not exist"));
		byte[] fileContent = blob.getContent();
		return Base64.getEncoder().encodeToString(fileContent);
	}
	
}
