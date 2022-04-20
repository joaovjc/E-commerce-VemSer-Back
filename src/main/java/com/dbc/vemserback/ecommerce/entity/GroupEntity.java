package com.dbc.vemserback.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "GROUP")
public class GroupEntity implements Serializable {

    @Id
    @Column(name = "groupId", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private List<UserEntity> users;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_rule",
            joinColumns = @JoinColumn(name = "groupId"),
            inverseJoinColumns = @JoinColumn(name = "ruleId")
    )
    private List<RuleEntity> rules;


}
