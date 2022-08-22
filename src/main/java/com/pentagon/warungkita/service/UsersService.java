package com.pentagon.warungkita.service;

import com.pentagon.warungkita.dto.UsersRequestDTO;
import com.pentagon.warungkita.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    List<Users> getAll();
    Users createUser(Users users);
    Optional<Users> getUserById(Long users_Id);
    Users findById(Long users_Id);
    Users updateUser(Users users) throws Exception;
    Users deleteUserById(Long users_Id);

    Optional<Users> findByUsername(String username);

    ResponseEntity<?> createUser(UsersRequestDTO usersRequestDTO);
}
