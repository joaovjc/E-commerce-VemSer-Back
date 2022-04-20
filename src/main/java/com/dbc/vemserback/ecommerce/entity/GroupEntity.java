package com.dbc.vemserback.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "GROUP_COMMERCE")
public class GroupEntity implements Serializable {

    @Id
    @Column(name = "group_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "groupEntity")
    private List<UserEntity> users;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_rule",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles;


}
