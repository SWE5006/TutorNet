package com.project.tutornet.model;

import com.project.tutornet.entity.UserInfoEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class UserDetailModel implements UserDetails {
    private final UserInfoEntity userInfoEntity;

    public UserDetailModel(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleText = "ROLE_";
        return Arrays
                .stream((roleText+userInfoEntity
                        .getRoles())
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() { return userInfoEntity.getPassword();}

    @Override
    public String getUsername() { return userInfoEntity.getEmailAddress(); }

    @Override
    public boolean isAccountNonExpired() { return false; }

    @Override
    public boolean isAccountNonLocked() { return false; }

    @Override
    public boolean isCredentialsNonExpired() { return false; }

    @Override
    public boolean isEnabled() { return false; }
}