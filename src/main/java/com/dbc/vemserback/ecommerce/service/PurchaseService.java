package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.repository.mongo.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseListRepository;

    public void createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, String idTopic) {
    	
//    	if(file!=null) {
//    		String fileName = file.getOriginalFilename();
//        	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.substring(fileName.lastIndexOf("."))))throw new BusinessRuleException("not a suported file type: "+fileName.substring(fileName.lastIndexOf(".")));
//
//    	}
    	
    	String originalFilename = file.getOriginalFilename();
    	
    	File convertToFile = null;
    	byte[] readAllBytes = null;
		try {
			convertToFile = this.convertToFile(file, originalFilename);
			readAllBytes = Files.readAllBytes(convertToFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	PurchaseEntity build = PurchaseEntity.builder()
    			.topicId(idTopic)
    			.name(purchaseDTO.getName())
    			.totalValue(new BigDecimal(purchaseDTO.getPrice()))
    			.fileName(originalFilename)
    			.file(readAllBytes).build();
    	
    	purchaseListRepository.save(build);
    	convertToFile.delete();
    	
    }

//    public List<PurchaseListCreateDTO> listTopics() {
//        return purchaseListRepository.findAll().stream().map(purchaseList -> objectMapper.convertValue(purchaseList, PurchaseListCreateDTO.class)).collect(Collectors.toList());
//    }
    
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}


}
