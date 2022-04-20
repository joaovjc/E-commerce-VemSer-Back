package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserCreateDTO;
import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final GroupService groupService;

    public List<LoginDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> objectMapper.convertValue(user, LoginDTO.class)).collect(Collectors.toList());
    }
    public UserCreateDTO createUser(UserCreateDTO userCreateDTO) throws Exception {

        UserEntity user = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        user.setGroupEntity(groupService.getById(Groups.USER.getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(userCreateDTO.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        return objectMapper.convertValue(savedUser, UserCreateDTO.class);

    }


}
