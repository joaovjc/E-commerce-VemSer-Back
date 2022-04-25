package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.user.LoginDTO;
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
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final GroupService groupService;
    private final FileService fileService;
    
    public UserLoginDto createUser(UserCreateDTO createDTO, MultipartFile file) throws BusinessRuleException {
    	if(this.findByEmail(createDTO.getEmail()).isPresent())throw new BusinessRuleException("Esse Email já existe");
    	
        UserEntity user = new UserEntity();
        
        user.setEmail(createDTO.getEmail());
        user.setFullName(createDTO.getFullName());
        user.setGroupEntity(groupService.getById(createDTO.getGroups().getGroupId()));
        user.setPassword(new BCryptPasswordEncoder().encode(createDTO.getPassword()));
        user.setProfileImage(file!=null?fileService.convertToByte(file):null);
        
        UserEntity savedUser = userRepository.save(user);
        byte[] profileImage = savedUser.getProfileImage();
        
        return UserLoginDto.builder().fullName(savedUser.getFullName()).username(user.getUsername()).profileImage(profileImage!=null?new String(profileImage):null).build();

    }

    public UserDTO updateUserbyAdmin(Groups group, Integer idUser) throws BusinessRuleException{
        UserEntity userEntity = userRepository.getById(idUser);
        userEntity.setGroupEntity(groupService.getById(group.getGroupId()));
        return objectMapper.convertValue(userRepository.save(userEntity), UserDTO.class);
    }

//    todo
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
        return findAllOrOrderByFullName;
    }


//    protected Integer getLogedUserId() {
//        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//    }

    //    todo
	public List<UserEntity> getByFullName(String nome) {
		return this.userRepository.getUserByFullName(nome);
	}


    protected UserEntity findById(Integer userId) throws BusinessRuleException {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessRuleException("Usuário não encontrado"));
    }
}
