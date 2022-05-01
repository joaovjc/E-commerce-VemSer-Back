package com.dbc.vemserback.ecommerce.service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.user.UserCreateDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserDTO;
import com.dbc.vemserback.ecommerce.dto.user.UserLoginDto;
import com.dbc.vemserback.ecommerce.dto.user.UserPageDTO;
import com.dbc.vemserback.ecommerce.entity.UserEntity;
import com.dbc.vemserback.ecommerce.enums.Groups;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final GroupService groupService;
    private final FileService fileService;
    
    public UserLoginDto createUser(UserCreateDTO createDTO, MultipartFile file) throws BusinessRuleException {
        log.info("Creating user");
    	//checa se o email existe
    	if(this.findByEmail(createDTO.getEmail()).isPresent())throw new BusinessRuleException("Esse Email já existe");
    	//cria um user
        UserEntity user = new UserEntity();
        user.setEmail(createDTO.getEmail());
        user.setFullName(createDTO.getFullName());
        user.setGroupEntity(groupService.getById(createDTO.getGroups().getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(createDTO.getPassword()));
        user.setProfileImage(file!=null?fileService.convertToByte(file):null);
        //salva o user
        UserEntity savedUser = userRepository.save(user);
        byte[] profileImage = savedUser.getProfileImage();
        
        //devolve um dto
        return UserLoginDto.builder().fullName(savedUser.getFullName()).username(user.getUsername()).profileImage(profileImage!=null?new String(profileImage):null).build();

    }
    //troca o status de um user para admin
    public UserDTO updateUserbyAdmin(Groups group, Integer idUser) throws BusinessRuleException{
        log.info("Updating user");
        UserEntity userEntity = userRepository.getById(idUser);
        userEntity.setGroupEntity(groupService.getById(group.getGroupId()));
        return objectMapper.convertValue(userRepository.save(userEntity), UserDTO.class);
    }
    
	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	//lista todos os user
    public Page<UserPageDTO> listUsersForAdmin(int page, String fullname) {
        log.info("Listing users");
        PageRequest pageRequest = PageRequest.of(
                page,
                5,
                Sort.Direction.ASC,
                "fullName");
        fullname = fullname==null?"":fullname;
        return userRepository.getUserByFullName(fullname, pageRequest);
    }
    
    public UserEntity findById(Integer userId) throws BusinessRuleException {
        log.info("Finding user by id");
        return userRepository.findById(userId).orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
    }
}
