package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dbc.vemserback.ecommerce.dto.*;
import com.dbc.vemserback.ecommerce.dto.user.PictureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.entity.PictureEntity;
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
    
    public UserLoginDto createUser(UserAdmDto createDTO, MultipartFile file) throws BusinessRuleException {
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
        	picture = new String(create.getPicture());
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

    public Page<UserPageDTO> listUsersForAdmin(int page) {
        PageRequest pageRequest = PageRequest.of(
                page,
                20,
                Sort.Direction.ASC,
                "fullName");
        Page<UserPageDTO> findAllOrOrderByFullName = userRepository.findAllOrOrderByFullName(pageRequest);
        
        findAllOrOrderByFullName.forEach(p->{
        	PictureEntity findByUserId = this.pictureService.findByUserId(p.getUserId());
        	p.setImage(findByUserId!=null?new String(findByUserId.getPicture()):null);
        });

        return findAllOrOrderByFullName;
    }


    protected Integer getLogedUserId() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

	public List<UserEntity> getByFullName(String nome) {
		return this.userRepository.getUserByFullName(nome);
	}


    protected UserEntity findById(Integer userId) throws BusinessRuleException {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
    }
}
