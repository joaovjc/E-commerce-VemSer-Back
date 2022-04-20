package com.dbc.vemserback.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity(name = "RULE")
public class RuleEntity implements Serializable, GrantedAuthority {

    @Id
    @Column(name = "ruleId", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ruleId;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "rules")
    private Set<GroupEntity> groups;

    @Override
    public String getAuthority() {
        return name;
    }
}
