package com.dbc.vemserback.ecommerce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.entity.ItemEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.TopicRepository;
import com.dbc.vemserback.ecommerce.service.TopicService;
import com.dbc.vemserback.ecommerce.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class TopicTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TopicService topicService;

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
        assertTrue(exception.getMessage().equals("voce não pode alterar um tópico que não é seu"));
    }

    @Test
    public void testOpenTopicStatusException(){
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.OPEN)
                .build();
        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.openTopic(1,1));
        assertTrue(exception.getMessage().equals("o topico não pode ser alterado pois já foi aberto"));
    }

    @Test
    public void testOpenTopicPurchaseException(){
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.CREATING)
                .build();
        List<ItemEntity> itemsList = new ArrayList<>();
        topic.setItems(itemsList);
        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.openTopic(1,1));
        assertTrue(exception.getMessage().equals("o topico tem que ter pelo menos um item"));
    }

    @Test
    public void testOpenTopicSave() throws BusinessRuleException {
        TopicEntity topic = TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.CREATING)
                .build();
        List<ItemEntity> itemsList = new ArrayList<>();
        ItemEntity itemEntity = ItemEntity.builder()
                 .value(BigDecimal.valueOf(25))
                .build();
        itemsList.add(itemEntity);
        itemsList.add(itemEntity);
        topic.setItems(itemsList);

        when(topicRepository.findById(any(Integer.class))).thenReturn(Optional.of(topic));
        when(topicRepository.save(any(TopicEntity.class))).thenReturn(topic);
        topicService.openTopic(1,1);
        verify(this.topicRepository, times(1)).save(any(TopicEntity.class));
    }

    @Test
    public void testTopicById() throws BusinessRuleException {
        TopicEntity topic = new TopicEntity();
        when(topicRepository.findById(any())).thenReturn(Optional.of(topic));
        topicService.topicById(1);
        verify(topicRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    public void testSave() {
        TopicEntity topic = new TopicEntity();
        when(topicRepository.save(any())).thenReturn(topic);
        topicService.save(topic);
        verify(topicRepository, times(1)).save(any(TopicEntity.class));
    }

    @Test
    public void  testDeleteByIdUserException() throws BusinessRuleException {
        TopicEntity topic= TopicEntity.builder()
                .topicId(1)
                .user(UserEntity.
                        builder()
                        .userId(2)
                        .build())
                .build();

        when(topicRepository.findById(1)).thenReturn(Optional.of(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.deleteById(1,1));
        assertTrue(exception.getMessage().equals("esse topico não pertence a esse user"));

    }
    @Test
    public void  testDeleteByIdStatusException() throws BusinessRuleException {
        TopicEntity topic= TopicEntity.builder()
                .userId(1)
                .status(StatusEnum.OPEN)
                .user(UserEntity.
                        builder()
                        .userId(1)
                        .build())
                .build();
        when(topicRepository.findById(1)).thenReturn(Optional.of(topic));
        Exception exception = assertThrows(BusinessRuleException.class, ()-> topicService.deleteById(1,1));
        assertTrue(exception.getMessage().equals("topico não pode ser deletado nesse status"));

    }
    @Test
    public void  testDeleteById() throws BusinessRuleException {
        TopicEntity topic= TopicEntity.builder()
                .topicId(1)
                .userId(1)
                .status(StatusEnum.CREATING)
                .user(UserEntity.
                        builder()
                        .userId(1)
                        .build())
                .build();

        when(topicRepository.findById(1)).thenReturn(Optional.of(topic));
        topicService.deleteById(1,1);
        verify(topicRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetTopicsManager() throws BusinessRuleException {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        PageRequest pageRequest = PageRequest.of(
                1,
                1,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "status"),
                        new Sort.Order(Sort.Direction.ASC, "date")));

        topicService.getTopics(simpleGrantedAuthorities, 1, 1,1,"Titulo");
        verify( topicRepository, times(1)).findAllByStatus(StatusEnum.OPEN, "Titulo", pageRequest);
    }

    @Test
    public void testGetTopicsFinancier() throws BusinessRuleException {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_FINANCIER"));
        PageRequest pageRequest = PageRequest.of(
                1,
                1,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "status"),
                        new Sort.Order(Sort.Direction.ASC, "date")));
        topicService.getTopics(simpleGrantedAuthorities, 1, 1,1,"Titulo");
        verify(topicRepository, times(1)).findAllByStatus(StatusEnum.MANAGER_APPROVED, "Titulo", pageRequest);
    }

    @Test
    public void testGetTopicsBuyer() throws BusinessRuleException {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_BUYER"));
        PageRequest pageRequest = PageRequest.of(
                1,
                1,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "status"),
                        new Sort.Order(Sort.Direction.ASC, "date")));

        topicService.getTopics(simpleGrantedAuthorities, 1, 1,1,"Titulo");
        verify( topicRepository, times(1)).findAllByStatusDifferent(StatusEnum.CREATING, "Titulo", pageRequest);
    }

    @Test
    public void testGetTopicsOthers() throws BusinessRuleException {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        PageRequest pageRequest = PageRequest.of(
                1,
                1,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "status"),
                        new Sort.Order(Sort.Direction.ASC, "date")));

        topicService.getTopics(simpleGrantedAuthorities, 1, 1,1,"Titulo");
        verify( topicRepository, times(1)).findAllByUserId(1, "Titulo", pageRequest);
    }

}
