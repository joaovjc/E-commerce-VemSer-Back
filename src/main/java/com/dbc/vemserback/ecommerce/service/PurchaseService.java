package com.dbc.vemserback.ecommerce.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.repository.mongo.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final FileService fileService;
	private final TopicService topicService;

	public boolean createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, String idTopic) {

//    	if(file!=null) {
//    		String fileName = file.getOriginalFilename();
//        	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.substring(fileName.lastIndexOf("."))))throw new BusinessRuleException("not a suported file type: "+fileName.substring(fileName.lastIndexOf(".")));
//
//    	}

		String originalFilename = file.getOriginalFilename();

		byte[] bytes = null;
		try {
			bytes = fileService.convertToByte(file, originalFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PurchaseEntity build = PurchaseEntity.builder().name(purchaseDTO.getName())
				.totalValue(new BigDecimal(purchaseDTO.getPrice())).fileName(originalFilename).file(bytes).build();

		PurchaseEntity save = purchaseRepository.save(build);
		
		return topicService.addPurchaseToTopic(idUser, idTopic, originalFilename);
	}

}
