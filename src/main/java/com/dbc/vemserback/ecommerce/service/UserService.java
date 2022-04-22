package com.dbc.vemserback.ecommerce.service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbc.vemserback.ecommerce.dto.CreateUserDTO;
import com.dbc.vemserback.ecommerce.dto.LoginDTO;
import com.dbc.vemserback.ecommerce.dto.PictureDTO;
import com.dbc.vemserback.ecommerce.dto.UserLoginDto;
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
    public UserLoginDto createUser(CreateUserDTO CreateDTO) throws BusinessRuleException {
    	
    	if(this.findByEmail(CreateDTO.getEmail()).isPresent())throw new BusinessRuleException("Esse Email já existe");
    	
        UserEntity user = objectMapper.convertValue(CreateDTO, UserEntity.class);
        
        user.setGroupEntity(groupService.getById(Groups.USER.getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(CreateDTO.getPassword()));
        
        UserEntity savedUser = userRepository.save(user);
        String picture = null;
        System.out.println(CreateDTO.getFile().getOriginalFilename());
        
        if(CreateDTO.getFile()!=null) {
        	PictureDTO create = pictureService.create(CreateDTO.getFile(),savedUser.getUserId());
        	picture = Base64.getEncoder().encodeToString(create.getPicture());
        }
        
        return UserLoginDto.builder().fullName(savedUser.getFullName()).username(user.getUsername()).profileImage(picture).build();

    }
	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


}
