package com.dbc.vemserback.ecommerce.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {
	
	 @PostMapping("/profile/pic")
	    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
	        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
//	        return fileService.upload(multipartFile);
			return null;
	    }

	    @PostMapping("/profile/pic/{fileName}")
	    public Object download(@PathVariable String fileName) throws IOException {
	        log.info("HIT -/download | File Name : {}", fileName);
//	        return fileService.download(fileName);
			return null;
	    }
	
}
