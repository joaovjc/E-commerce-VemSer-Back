package com.dbc.vemserback.ecommerce;

import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;
import com.dbc.vemserback.ecommerce.service.TopicService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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


    public void shouldSuccesfulCreate(){
        UserEntity user = UserEntity.builder()
                .userId(1)
                .build();
        TopicEntity topic = TopicEntity.builder()
                .build();


    }

}
