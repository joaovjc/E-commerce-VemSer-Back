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
@Entity(name = "ROLE_COMMERCE")
public class RoleEntity implements Serializable, GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6814700043291097443L;

	@Id
    @Column(name = "role_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<GroupEntity> groups;

    @Override
    public String getAuthority() {
        return name;
    }
}
