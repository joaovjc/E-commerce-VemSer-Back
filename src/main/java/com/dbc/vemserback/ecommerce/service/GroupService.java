package com.dbc.vemserback.ecommerce.service;

import com.dbc.vemserback.ecommerce.entity.GroupEntity;
import com.dbc.vemserback.ecommerce.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupEntity getById(Integer groupId) throws Exception {
        return groupRepository.findById(groupId).orElseThrow((() -> new Exception("Group not found")));
    }
}
