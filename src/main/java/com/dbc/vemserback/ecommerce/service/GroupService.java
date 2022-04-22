package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.GroupRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupEntity getById(Integer groupId) throws BusinessRuleException {
        return groupRepository.findById(groupId).orElseThrow((() -> new BusinessRuleException("Group not found")));
    }
}
