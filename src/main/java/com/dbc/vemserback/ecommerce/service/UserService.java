package com.dbc.vemserback.ecommerce.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dbc.vemserback.ecommerce.dto.*;
import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.UserRepository;
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
    public UserLoginDto createUser(UserAdmDto createDTO, MultipartFile file) throws BusinessRuleException {
    	if(file!=null) {
    		String fileName = file.getOriginalFilename();
        	if(!Arrays.asList(".png",".jpg",".jpeg").contains(fileName.substring(fileName.lastIndexOf("."))))throw new BusinessRuleException("not a suported file type: "+fileName.substring(fileName.lastIndexOf(".")));

    	}
    	if(this.findByEmail(createDTO.getEmail()).isPresent())throw new BusinessRuleException("Esse Email já existe");
    	
        UserEntity user = new UserEntity();
        
        user.setEmail(createDTO.getEmail());
        user.setFullName(createDTO.getFullName());
        user.setGroupEntity(groupService.getById(createDTO.getGroups().getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(createDTO.getPassword()));
        
        UserEntity savedUser = userRepository.save(user);
        String picture = null;
        if(file!=null) {
        	PictureDTO create = pictureService.create(file,savedUser.getUserId());
        	picture = Base64.getEncoder().encodeToString(create.getPicture());
        }
        
        return UserLoginDto.builder().fullName(savedUser.getFullName()).username(user.getUsername()).profileImage(picture).build();

    }

    public UserDTO updateUserbyAdmin(Groups group, Integer idUser) throws BusinessRuleException{
        UserEntity userEntity = userRepository.getById(idUser);
        userEntity.setGroupEntity(groupService.getById(group.getGroupId()));
        return objectMapper.convertValue(userRepository.save(userEntity), UserDTO.class);
    }

	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


    protected Integer getLogedUserId() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


}
