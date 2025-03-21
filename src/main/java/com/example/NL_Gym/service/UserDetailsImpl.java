package com.example.NL_Gym.service;

import com.example.NL_Gym.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private final Integer id;
    private final String email;
    private final String password;
    private final Role role;

    public UserDetailsImpl(Integer id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public Role getRole() {
        return role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority( role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return "";
    }


}
