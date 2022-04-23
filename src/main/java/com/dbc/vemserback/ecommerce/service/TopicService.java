package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;
import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.repository.mongo.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

	private final TopicRepository topicRepository;
	private final ObjectMapper objectMapper;

	public String createTopic(TopicDTO dto, Integer userId) {

		TopicEntity entity = TopicEntity.builder().date(LocalDate.now()).status(StatusEnum.OPEN).title(dto.getTitle())
				.totalValue(BigDecimal.ZERO).userId(userId).build();

		entity = topicRepository.insert(entity);

		return entity.getTopicId();
	}

	public void createPurchase(PurchaseDTO purchaseDTO, MultipartFile file, int idUser, String idTopic) {

//    	if(file!=null) {
//    		String fileName = file.getOriginalFilename();
//        	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.substring(fileName.lastIndexOf("."))))throw new BusinessRuleException("not a suported file type: "+fileName.substring(fileName.lastIndexOf(".")));
//
//    	}
		String originalFilename = file.getOriginalFilename();
		
		TopicEntity findById = topicRepository.findById(idTopic).orElseThrow();

		File convertToFile = null;
		byte[] readAllBytes = null;
		try {
			convertToFile = this.convertToFile(file, originalFilename);
			readAllBytes = Files.readAllBytes(convertToFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		PurchaseEntity build = PurchaseEntity.builder().topicId(idTopic).name(purchaseDTO.getName())
				.totalValue(new BigDecimal(purchaseDTO.getPrice())).fileName(originalFilename).file(readAllBytes)
				.build();
		
		findById.getPucharses().add(build);
		
		this.topicRepository.insert(findById);
		
//    	purchaseListRepository.save(build);
		convertToFile.delete();

	}

	private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}

	public List<TopicCreateDTO> listTopics() {
		return topicRepository.findAll().stream().map(topic -> objectMapper.convertValue(topic, TopicCreateDTO.class))
				.collect(Collectors.toList());
	}

}
