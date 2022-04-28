package com.dbc.vemserback.ecommerce;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.Item.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemFullDTO;
import com.dbc.vemserback.ecommerce.entity.PurchaseEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.PurchaseRepository;
import com.dbc.vemserback.ecommerce.service.FileService;
import com.dbc.vemserback.ecommerce.service.ItemService;
import com.dbc.vemserback.ecommerce.service.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ItemTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private FileService fileService;
    @Mock
    private TopicService topicService;
    @Mock
    private MultipartFile multipartFile;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private PurchaseEntity purchaseEntity;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateItemWithFileNull() {
        ItemCreateDTO item = new ItemCreateDTO();
        MultipartFile file = null;

        assertThrows(BusinessRuleException.class, () -> itemService.createPurchase(item, file, 1, 1));

    }

    @Test
    public void testCreateItemWithTopicNull() throws BusinessRuleException {
        ItemCreateDTO item = new ItemCreateDTO();
        MultipartFile file = multipartFile;

        doThrow(BusinessRuleException.class).when(topicService).topicById(any());

        assertThrows(BusinessRuleException.class, () -> itemService.createPurchase(item, file, 1, 1));
    }

    @Test
    public void testCreateItemWithStatusNotCreating() throws BusinessRuleException {
        ItemCreateDTO item = new ItemCreateDTO();
        MultipartFile file = multipartFile;
        TopicEntity topic = TopicEntity.builder().topicId(1).build();
        topic.setStatus(StatusEnum.OPEN);

        when(topicService.topicById(any())).thenReturn(topic);

        assertThrows(BusinessRuleException.class, () -> itemService.createPurchase(item, file, 1, 1));
    }


    @Test
    public void testCreateItem() throws BusinessRuleException {
        ItemCreateDTO item = new ItemCreateDTO();
        MultipartFile file = multipartFile;
        TopicEntity topic = TopicEntity.builder().topicId(1).build();
        topic.setStatus(StatusEnum.CREATING);
        ItemFullDTO itemFullDTO = ItemFullDTO.builder().itemId(1).build();

        when(topicService.topicById(any())).thenReturn(topic);

        itemService.createPurchase(item, file, 1, 1);
        verify(purchaseRepository, times(1)).save(any());
    }

    @Test
    public void testListPurchasesByTopicIdWithTopicNull() throws BusinessRuleException {
        doThrow(BusinessRuleException.class).when(topicService).topicById(any());

        assertThrows(BusinessRuleException.class, () -> itemService.listPurchasesByTopicId(1));
    }

    @Test
    public void testListPurchasesByTopicId() throws BusinessRuleException {
        TopicEntity topic = TopicEntity.builder().topicId(1).build();

        when(topicService.topicById(any())).thenReturn(topic);

        itemService.listPurchasesByTopicId(1);
        verify(purchaseRepository, times(1)).findAllByTopicId(any());
    }

    @Test
    public void testDeleteByIdWithUserIdNotAuthenticatedUser() throws BusinessRuleException {

        PurchaseEntity purchase = PurchaseEntity.builder().purchaseId(1).build();
        UserEntity user = UserEntity.builder().userId(1).build();
        TopicEntity topic = TopicEntity.builder().topicId(1).user(user).userId(user.getUserId()).purchases(new ArrayList<>()).build();
        topic.setStatus(StatusEnum.CREATING);
        purchase.setTopicEntity(topic);

        when(purchaseRepository.findById(any())).thenReturn(Optional.of(purchase));
        assertThrows(BusinessRuleException.class, () -> itemService.deleteById(purchase.getPurchaseId(), 12312));
    }

    @Test
    public void testDeleteByIdWithTopicStatusNotCreating() throws BusinessRuleException {
        PurchaseEntity purchase = PurchaseEntity.builder().purchaseId(1).build();
        UserEntity user = UserEntity.builder().userId(1).build();
        TopicEntity topic = TopicEntity.builder().topicId(1).user(user).userId(user.getUserId()).purchases(new ArrayList<>()).build();
        topic.setStatus(StatusEnum.OPEN);
        purchase.setTopicEntity(topic);

        when(purchaseRepository.findById(any())).thenReturn(Optional.of(purchase));

        assertThrows(BusinessRuleException.class, () -> itemService.deleteById(purchase.getPurchaseId(), purchase.getTopicEntity().getUserId()));
    }

    @Test
    public void testDeleteById() throws BusinessRuleException {
       PurchaseEntity purchase = PurchaseEntity.builder().purchaseId(1).build();
       UserEntity user = UserEntity.builder().userId(1).build();
       TopicEntity topic = TopicEntity.builder().topicId(1).user(user).userId(user.getUserId()).purchases(new ArrayList<>()).build();
       topic.setStatus(StatusEnum.CREATING);
       purchase.setTopicEntity(topic);

       when(purchaseRepository.findById(any())).thenReturn(Optional.of(purchase));
       itemService.deleteById(purchase.getPurchaseId(), purchase.getTopicEntity().getUserId());

       verify(purchaseRepository, times(1)).delete(purchase);
    }

}
