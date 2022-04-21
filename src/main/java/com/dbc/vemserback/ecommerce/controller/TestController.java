package com.dbc.vemserback.ecommerce.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/test")
@Log4j2
@RequiredArgsConstructor
public class TestController {
	private final FileService fileService;
	
	 @PostMapping("/profile/pic")
	    public Object upload(@RequestParam("file") MultipartFile multipartFile) throws BusinessRuleException {
	        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
	        return fileService.uploadImage(multipartFile);
	    }

	    @PostMapping("/profile/pic/{fileName}")
	    public Object download(@PathVariable String fileName) throws BusinessRuleException {
	        log.info("HIT -/download | File Name : {}", fileName);
	        return fileService.findImageByName(fileName);
	        
	    }
}
