package com.dbc.vemserback.ecommerce.controller;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbc.vemserback.ecommerce.dto.TopicDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
//	@GetMapping()
//	public String test() {
//		return "teste topico create";
//	}
	
	@PostMapping("/create")
	public void cadastrar(@ModelAttribute(name = "data") TopicDTO topics) {
		
		System.out.println(topics.getPurchases().get(0).getFile().getOriginalFilename());
		
	}
	
}
