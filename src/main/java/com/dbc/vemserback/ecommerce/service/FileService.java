package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	public byte[] convertToByte(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		
		byte[] readAllBytes = null;
		try {
			readAllBytes = Files.readAllBytes(tempFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		tempFile.delete();
		return readAllBytes;
	}
	
	
	
}
