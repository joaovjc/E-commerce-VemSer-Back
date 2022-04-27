package com.dbc.vemserback.ecommerce;

import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;
import com.dbc.vemserback.ecommerce.service.TopicService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TopicService topicService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(topicService,"objectMapper",objectMapper);
    }

    @Test
    public void testCreateTopic() throws BusinessRuleException {
        UserEntity user = UserEntity.builder()
                .userId(1)
                .build();
        TopicEntity topic = TopicEntity.builder()
                .build();
        TopicCreateDTO dto = TopicCreateDTO.builder()
                .title("Title test")
                .build();

        when(userService.findById(any())).thenReturn(user);
        when(topicRepository.save(any())).thenReturn(topic);
        topicService.createTopic(dto,1);
        verify(topicRepository, times(1)).save(any(TopicEntity.class));
    }

    public void testManagerAproveOrReproveTopic(){
    }

}
