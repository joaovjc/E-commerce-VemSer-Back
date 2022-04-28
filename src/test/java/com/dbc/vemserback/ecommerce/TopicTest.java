package com.dbc.vemserback.ecommerce;

import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.entity.ItemEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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

    @Test
    public void testUpdateFinancierTopicFalse() throws BusinessRuleException {
        TopicEntity topic = TopicEntity.builder()
                .build();

        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        topicService.updateFinancierTopic(1,false);
        assertEquals(StatusEnum.FINANCIALLY_REPROVED, topic.getStatus());
        verify(topicRepository, times(1)).save(any(TopicEntity.class));

    }
    @Test
    public void testUpdateFinancierTopicTrue() throws BusinessRuleException {
        TopicEntity topic = TopicEntity.builder()
                .build();

        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        topicService.updateFinancierTopic(1,true);
        assertEquals(StatusEnum.CONCLUDED, topic.getStatus());
        verify(topicRepository, times(1)).save(any(TopicEntity.class));
    }

    @Test
    public void testOpenTopicUserIdException(){
        TopicEntity topic = TopicEntity.builder()
                .userId(0)
                .build();
        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.openTopic(1,1));
        assertTrue(exception.getMessage().equals("The topic is not owned by you, thus you cannot change it!!!"));
    }

    @Test
    public void testOpenTopicStatusException(){
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.OPEN)
                .build();
        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.openTopic(1,1));
        assertTrue(exception.getMessage().equals("The topic was already opened!!!"));
    }

    @Test
    public void testOpenTopicPurchaseException(){
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.CREATING)
                .build();
        List<ItemEntity> purchaseEntities = new ArrayList<>();
        topic.setPurchases(purchaseEntities);
        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.openTopic(1,1));
        assertTrue(exception.getMessage().equals("The topic must have at least one"));
    }

    @Test
    public void testOpenTopicSave() throws BusinessRuleException {
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.CREATING)
                .build();
        List<ItemEntity> purchaseEntities = new ArrayList<>();
        ItemEntity itemEntity = ItemEntity.builder()
                 .value(BigDecimal.valueOf(25))
                .build();
        purchaseEntities.add(itemEntity);
        purchaseEntities.add(itemEntity);
        topic.setPurchases(purchaseEntities);

        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        topicService.openTopic(1,1);
        verify(this.topicRepository, times(1)).save(any(TopicEntity.class));
    }



}
