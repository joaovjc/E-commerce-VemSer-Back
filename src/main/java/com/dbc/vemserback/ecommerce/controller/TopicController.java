package com.dbc.vemserback.ecommerce.controller;


import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;


    @PostMapping("/create")
    public TopicCreateDTO createTopic(@RequestBody TopicCreateDTO topicCreateDTO) {
      return  topicService.createTopic(topicCreateDTO);
    }

    @GetMapping("/get-all")
    public List<TopicCreateDTO> getAllTopics() {
        return topicService.listTopics();
    }

}
