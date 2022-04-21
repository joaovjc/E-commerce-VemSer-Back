package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper;

    public TopicCreateDTO createTopic(TopicCreateDTO topicCreateDTO) {
      TopicEntity topic = topicRepository.save(objectMapper.convertValue(topicCreateDTO, TopicEntity.class));

      return objectMapper.convertValue(topic, TopicCreateDTO.class);
    }

    public List<TopicCreateDTO> listTopics() {
        return topicRepository.findAll().stream().map(topic -> objectMapper.convertValue(topic, TopicCreateDTO.class)).collect(Collectors.toList());
    }


}
