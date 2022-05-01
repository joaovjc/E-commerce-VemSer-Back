package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    
    public GroupEntity getById(Integer groupId) throws BusinessRuleException {
        log.info("Getting group by id: {}", groupId);
        return groupRepository.findById(groupId).orElseThrow((() -> new BusinessRuleException("Grupo não encontrado")));
    }
}
