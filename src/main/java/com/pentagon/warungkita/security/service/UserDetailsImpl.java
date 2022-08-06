package com.pentagon.warungkita.security.service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.UsersRepo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    private UsersRepo usersRepo;

    private String email;
    private String fullName;
    private String username;
    @JsonIgnore
    private String password;
;

    public UserDetailsImpl(String email, String fullName, String username, String password) {
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.password = password;

    }

    public static UserDetailsImpl build(Users users){
        return new UserDetailsImpl(
                users.getEmail(),
                users.getFullName(),
                users.getUsername(),
                users.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Users user = new Users();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
