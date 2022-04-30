package com.dbc.vemserback.ecommerce;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.GroupRepository;
import com.dbc.vemserback.ecommerce.service.GroupService;

@RunWith(MockitoJUnitRunner.class)
public class GroupTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetByIdWithIdNull() {
        assertThrows(BusinessRuleException.class, () -> groupService.getById(1));

    }

    @Test
    public void testGetById() throws BusinessRuleException {
        GroupEntity groupEntity = new GroupEntity();

        when(groupRepository.findById(any())).thenReturn(Optional.of(groupEntity));
        groupService.getById(1);

        verify(groupRepository, times(1)).findById(any());
    }
}
