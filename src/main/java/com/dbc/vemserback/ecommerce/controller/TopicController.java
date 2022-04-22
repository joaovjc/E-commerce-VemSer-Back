package com.dbc.vemserback.ecommerce.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
//    private final TopicService topicService;
//    @PostMapping("/create")
//    public TopicCreateDTO createTopic(@RequestBody TopicCreateDTO topicCreateDTO) {
//      return  topicService.createTopic(topicCreateDTO);
//    }
//
//    @GetMapping("/get-all")
//    public List<TopicCreateDTO> getAllTopics() {
//        return topicService.listTopics();
//    }
	
	@GetMapping()
	public String test() {
		return "teste topico create";
	}
	
}
