package com.pentagon.warungkita.security.service;


import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " tidak ditemukan"));
//            if (user == null) {
//                log.error("User not found");
//                throw new UsernameNotFoundException("User not found" + username);
//            } else {
//                log.info("User found: {}", username);
//            }
            return UserDetailsImpl.build(user);

    }

}