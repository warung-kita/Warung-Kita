package com.pentagon.warungkita.security.service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.UsersRepo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

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
    @JsonIgnore
    private String roles;
;

    public UserDetailsImpl(String email, String fullName, String username, String password, List<Roles> roles) {
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.roles = String.valueOf(roles);

    }

    public static UserDetailsImpl build(Users users){
        return new UserDetailsImpl(
                users.getEmail(),
                users.getFullName(),
                users.getUsername(),
                users.getPassword(),
                users.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.hasText(roles)) {
            String[] splits = roles.replaceAll(" ", "").split(",");
            for (String string : splits) {
                authorities.add(new SimpleGrantedAuthority(string));
            }
        }
        return authorities;
//        Users user = usersRepo.findByUsername(username);
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        });
//        return authorities;
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
