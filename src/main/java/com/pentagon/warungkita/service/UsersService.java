package com.pentagon.warungkita.service;

import com.pentagon.warungkita.model.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    List<Users> getAll();
    Users createUser(Users users);
    Optional<Users> getUserById(Long users_Id);
    Users updateUser(Users users) throws Exception;
    void deleteUserById(Long users_Id);

}
