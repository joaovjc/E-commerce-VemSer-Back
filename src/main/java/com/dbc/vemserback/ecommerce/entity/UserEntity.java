package com.dbc.vemserback.ecommerce.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "USER_COMMERCE")
public class UserEntity implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8954048764906045648L;

	@Id
    @Column(name = "user_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image")
    private byte[] profileImage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(referencedColumnName = "group_id", name = "group_id")
    private GroupEntity groupEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TopicEntity> topics;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuotationEntity> quotations;


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>(groupEntity.getRoles());
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
