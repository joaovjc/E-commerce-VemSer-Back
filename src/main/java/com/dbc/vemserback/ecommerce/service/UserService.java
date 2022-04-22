package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.UserCreateDTO;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final ObjectMapper objectMapper;
    private final GroupService groupService;

    public List<LoginDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> objectMapper.convertValue(user, LoginDTO.class)).collect(Collectors.toList());
    }
    //TODO revisar a logica
    public UserCreateDTO createUser(UserCreateDTO userCreateDTO, MultipartFile file) throws BusinessRuleException {
    	
    	if(this.findByEmail(userCreateDTO.getEmail()).isPresent())throw new BusinessRuleException("Esse Email já existe");
    	
        UserEntity user = objectMapper.convertValue(userCreateDTO, UserEntity.class);
        
        user.setGroupEntity(groupService.getById(Groups.USER.getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(userCreateDTO.getPassword()));
        
        UserEntity savedUser = userRepository.save(user);
        
        String create = pictureService.create(file,savedUser.getUserId());
        
        return objectMapper.convertValue(savedUser, UserCreateDTO.class);

    }
	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


}
