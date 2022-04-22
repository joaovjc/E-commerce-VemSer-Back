package com.dbc.vemserback.ecommerce.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
//	@GetMapping()
//	public String test() {
//		return "teste topico create";
//	}

//	public void cadastrar(@ModelAttribute(name = "data") TopicCreateDTO topic,
//	public void cadastrar(@RequestAttribute List<PurchaseDTO> purchases, @RequestPart List<MultipartFile> file) {
	@PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE}, path = "/create")
	public void create(@RequestParam Map<String, String> allParams, HttpServletRequest request) {
		Map<String, MultipartFile> fileMap = new HashMap<String, MultipartFile>();
		if (request instanceof MultipartHttpServletRequest) {
		    MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		    fileMap = multiRequest.getFileMap();
		}
		fileMap.forEach((k,v)-> System.out.println("Key: "+k+" value: "+v.getOriginalFilename()));
	}

}
