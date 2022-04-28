package com.dbc.vemserback.ecommerce;

import com.dbc.vemserback.ecommerce.entity.QuotationEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.TopicService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dbc.vemserback.ecommerce.repository.post.QuotationRepository;
import com.dbc.vemserback.ecommerce.service.QuotationService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuotationTest {

    @Mock
    private QuotationRepository quotationRepository;
    @Mock
    private  ObjectMapper objectMapper;
    @Mock
    private  UserService userService;
    @Mock
    private TopicService topicService;

    @InjectMocks
    private QuotationService quotationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateQuotation() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();
        UserEntity userEntity = UserEntity.builder().userId(1).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);
        quotationService.createQuotation(topicEntity.getTopicId(), 200.00, userEntity.getUserId());

        verify(quotationRepository, times(1)).save(any(QuotationEntity.class));
    }

    @Test
    public void testCreateQuotationWithNullQuotation() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();
        UserEntity userEntity = UserEntity.builder().userId(1).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);
        doThrow(NullPointerException.class).when(quotationRepository).save(any(QuotationEntity.class));

        assertThrows(NullPointerException.class, () -> quotationService.createQuotation(topicEntity.getTopicId(), 200.00, userEntity.getUserId()));
    }

    @Test
    public void testCreateQuotationWithNullTopic() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();
        UserEntity userEntity = UserEntity.builder().userId(1).build();

        doThrow(BusinessRuleException.class).when(topicService).topicById(any());

        assertThrows(BusinessRuleException.class, () -> quotationService.createQuotation(topicEntity.getTopicId(), 200.00, userEntity.getUserId()));
    }

    @Test
    public void testCreateQuotationWithNullUser() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();
        UserEntity userEntity = UserEntity.builder().userId(1).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);
        doThrow(BusinessRuleException.class).when(userService).findById(any());

        assertThrows(BusinessRuleException.class, () -> quotationService.createQuotation(topicEntity.getTopicId(), 200.00, userEntity.getUserId()));
    }


    @Test
    public void testCreateQuotationWithNotOpenTopic() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.CONCLUDED).build();
        UserEntity userEntity = UserEntity.builder().userId(1).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);

        assertThrows(BusinessRuleException.class, () -> quotationService.createQuotation(topicEntity.getTopicId(), 200.00, userEntity.getUserId()));
    }

    @Test
    public void testManagerUpdateWithNullTopic() throws BusinessRuleException {
        QuotationEntity quotationEntity = new QuotationEntity();
        TopicEntity topicEntity = new TopicEntity();

        doThrow(BusinessRuleException.class).when(topicService).topicById(any());

        assertThrows(BusinessRuleException.class, () -> quotationService.managerAproveOrReproveTopic(topicEntity.getTopicId(), quotationEntity.getQuotationId(), true));
    }

    @Test
    public void testManagerUpdateTopicWithStatusNotOpen() throws BusinessRuleException {
        QuotationEntity quotationEntity = new QuotationEntity();
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setStatus(StatusEnum.CONCLUDED);

        when(topicService.topicById(any())).thenReturn(topicEntity);

        assertThrows(BusinessRuleException.class, () -> quotationService.managerAproveOrReproveTopic(topicEntity.getTopicId(), quotationEntity.getQuotationId(), true));
    }

    @Test
    public void testManagerUpdateTopicWithQuotationSizeOne() throws BusinessRuleException {
        QuotationEntity quotationEntity = new QuotationEntity();
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);
        topicEntity.setQuotations(new ArrayList<>());
        topicEntity.getQuotations().add(quotationEntity);

        assertThrows(BusinessRuleException.class, () -> quotationService.managerAproveOrReproveTopic(topicEntity.getTopicId(), quotationEntity.getQuotationId(), true));
    }

    @Test
    public void testManagerUpdateTopicTrue() throws BusinessRuleException {
        QuotationEntity quotationEntity = QuotationEntity.builder().quotationId(1).build();
        QuotationEntity quotationEntity2 =QuotationEntity.builder().quotationId(2).build();
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();

        when(topicService.topicById(any())).thenReturn(topicEntity);
        topicEntity.setQuotations(new ArrayList<>());
        topicEntity.getQuotations().add(quotationEntity);
        topicEntity.getQuotations().add(quotationEntity2);
        quotationService.managerAproveOrReproveTopic(topicEntity.getTopicId(), quotationEntity.getQuotationId(), true);

       assertEquals(StatusEnum.MANAGER_APPROVED, quotationEntity.getQuotationStatus());
       assertEquals(StatusEnum.MANAGER_REPROVED, quotationEntity2.getQuotationStatus());
       assertEquals(StatusEnum.MANAGER_APPROVED, topicEntity.getStatus());
       verify(topicService, times(1)).save(topicEntity);
    }

    @Test
    public void testManagerUpdateTopicFalse() throws BusinessRuleException {
        QuotationEntity quotationEntity = QuotationEntity.builder().quotationId(1).build();
        QuotationEntity quotationEntity2 =QuotationEntity.builder().quotationId(2).build();
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).status(StatusEnum.OPEN).build();

       when(topicService.topicById(any())).thenReturn(topicEntity);
       topicEntity.setQuotations(new ArrayList<>());
       topicEntity.getQuotations().add(quotationEntity);
       topicEntity.getQuotations().add(quotationEntity2);
       quotationService.managerAproveOrReproveTopic(topicEntity.getTopicId(), quotationEntity.getQuotationId(), false);

       assertEquals(StatusEnum.MANAGER_REPROVED, topicEntity.getStatus());
       assertEquals(StatusEnum.MANAGER_REPROVED, quotationEntity.getQuotationStatus());
       assertEquals(StatusEnum.MANAGER_REPROVED, quotationEntity2.getQuotationStatus());
       verify(topicService, times(1)).save(topicEntity);
    }


    @Test
    public void testQuotationByTopicWithTopicNull() throws BusinessRuleException {
        TopicEntity topicEntity = new TopicEntity();
        List<SimpleGrantedAuthority> list = new ArrayList<>();

        doThrow(BusinessRuleException.class).when(topicService).topicById(any());

        assertThrows(BusinessRuleException.class, () -> quotationService.quotationsByTopic(topicEntity.getTopicId(), list));
    }

    @Test
    public void testQuotationByTopicWithRoleFinancier() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).build();
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_FINANCIER"));

        when(topicService.topicById(any())).thenReturn(topicEntity);
        quotationService.quotationsByTopic(topicEntity.getTopicId(), list);
        verify(quotationRepository, times(1)).findAllByTopicIdWhereStatusApproved(topicEntity.getTopicId());
    }

    @Test
    public void testQuotationByTopicWithAnyRole() throws BusinessRuleException {
        TopicEntity topicEntity = TopicEntity.builder().topicId(1).build();
        QuotationEntity quotationEntity = QuotationEntity.builder().quotationId(1).build();

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        topicEntity.setQuotations(new ArrayList<>());
        topicEntity.getQuotations().add(quotationEntity);
        when(topicService.topicById(any())).thenReturn(topicEntity);
        quotationService.quotationsByTopic(topicEntity.getTopicId(), list);

        assertTrue(topicEntity.getQuotations().contains(quotationEntity));
    }

    @Test
    public void testFindQuotationByIdWithIdNull() {
        assertThrows(BusinessRuleException.class, () -> quotationService.findQuotationById(null));
    }

    @Test
    public void testFindQuotationById() throws BusinessRuleException {
        QuotationEntity quotationEntity = QuotationEntity.builder().quotationId(1).build();

        when(quotationRepository.findById(any())).thenReturn(Optional.ofNullable(quotationEntity));
        quotationService.findQuotationById(quotationEntity.getQuotationId());

        verify(quotationRepository, times(1)).findById(any());
    }




}
