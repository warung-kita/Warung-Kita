package com.pentagon.warungkita.repository;

import com.pentagon.warungkita.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
//    Optional<Users> findByUsername(String username);
//    Optional<Users> findByEmailAddress(String emailAddress);
    Boolean existsByUsername(String username);
//    Boolean existsByEmailAddress(String emailAddress);
}
