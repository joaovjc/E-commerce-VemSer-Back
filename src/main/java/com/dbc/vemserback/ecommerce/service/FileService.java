package com.dbc.vemserback.ecommerce.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

@Service
public class FileService {
	
	public byte[] convertToByte(MultipartFile multipartFile) throws BusinessRuleException {
		String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.toLowerCase())) {
    		throw new BusinessRuleException("not a suported file type: "+fileName);
    	}
		
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] readAllBytes = resizeImage(tempFile);
		tempFile.delete();
		
		return Base64.getEncoder().encode(readAllBytes);
	}
	
	private byte[] resizeImage(File originalImage) throws BusinessRuleException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
			Thumbnails.of(originalImage)
			    .size(100, 100)
			    .outputFormat("JPEG")
			    .outputQuality(0.50)
			    .toOutputStream(outputStream);
		} catch (IOException e) {
			throw new BusinessRuleException(e.getMessage());
		}
		return outputStream.toByteArray();
	}
}
