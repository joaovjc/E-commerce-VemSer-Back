package com.dbc.vemserback.ecommerce.entity;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "GROUP_COMMERCE")
public class GroupEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3472639934749272065L;

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
            name = "role_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles;

}
