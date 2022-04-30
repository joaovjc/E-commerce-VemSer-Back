package com.dbc.vemserback.ecommerce;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dbc.vemserback.ecommerce.dto.user.UserCreateDTO;
import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.UserRepository;
import com.dbc.vemserback.ecommerce.service.GroupService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupService groupService;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() throws BusinessRuleException {
        UserEntity user = UserEntity.builder()
                .profileImage(null)
                .build();
        GroupEntity group = new GroupEntity();
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .email("test@test.com")
                .fullName("teste teste")
                .password("Test@123")
                .groups(Groups.USER)
                .build();
        when(groupService.getById(any(Integer.class))).thenReturn(group);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        userService.createUser(userCreateDTO, null);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testUpdateUserByAdmin() throws BusinessRuleException {
        UserEntity user = UserEntity.builder().build();
        GroupEntity group = new GroupEntity();

        when(userRepository.getById(any(Integer.class))).thenReturn(user);
        when(groupService.getById(any(Integer.class))).thenReturn(group);
        userService.updateUserbyAdmin(Groups.USER, 1);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
    @Test
    public void testFindByEmail(){
        UserEntity user = UserEntity.builder().userId(1).build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        userService.findByEmail("test@test.com");
        verify(userRepository, times(1)).findByEmail("test@test.com");

    }
    @Test
    public void testFindById() throws BusinessRuleException {
        UserEntity user = UserEntity.builder().userId(1).build();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.findById(1);
        verify(userRepository, times(1)).findById(1);

    }

//    @Test
//    public void testListUsersForAdminNull(){
//        userService.listUsersForAdmin(1, null);
//        verify(userRepository, times(1)).findAllOrOrderByFullName(any());
//    }

    @Test
    public void testListUsersForAdmin(){
        userService.listUsersForAdmin(1, "Teste teste");
        verify(userRepository, times(1)).getUserByFullName(any(String.class), any());
    }

}
