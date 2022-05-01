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

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class FileService {
	
	public byte[] convertToByte(MultipartFile multipartFile) throws BusinessRuleException {
		//checa se o tipo do arquivo é um dos aceitos
		log.info("#### METHOD -> convertToByte ####");
		String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.toLowerCase())) {
    		throw new BusinessRuleException("Esse tipo de arquivo não é suportado: "+fileName);
    	}
		
    	//converte para um file do java
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			throw new BusinessRuleException(e.getMessage());
		}
		
		//reduz o tamanho da imagem
		byte[] readAllBytes = resizeImage(tempFile);
		tempFile.delete();
		
		//devolve a imagem já em base 64
		return Base64.getEncoder().encode(readAllBytes);
	}
	
	private byte[] resizeImage(File originalImage) throws BusinessRuleException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
	    	//recebe um file e converte para uma ibyte output stream
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
