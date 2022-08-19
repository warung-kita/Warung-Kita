package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.RolesRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users findById(Long users_Id) {
        return usersRepo.findById(users_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Pengguna dengan id " + users_Id + " tidak ditemukan"));

    }

    @Override
    public List<Users> getAll() {
        List<Users> users = usersRepo.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :");
        }
        return this.usersRepo.findAll();
    }

    @Override
    public Users createUser(Users users) {
        /**
         * Logic add role buyer when creating new user
         * */
        if(users.getRoles() == null){
            Roles role = rolesRepo.findByName("ROLE_BUYER");
            List<Roles> roles = new ArrayList<>();
            roles.add(role);
            users.setRoles(roles);
            users.setActive(true);
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return this.usersRepo.save(users);
    }

    public Optional<Users> getUserById(Long users_Id) {
        Optional<Users> optionalUser = usersRepo.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        return this.usersRepo.findById(users_Id);
    }

    @Override
    public Users updateUser(Users users) {
        Optional<Users> optionalUser = usersRepo.findById(users.getUserId());
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users.getUserId());
        }
        users.setActive(true);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return this.usersRepo.save(users);
    }

    @Override
    public Users deleteUserById(Long users_Id) {
        Optional<Users> optionalUser = usersRepo.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        Users users = usersRepo.getReferenceById(users_Id);
        users.setActive(false);
        return this.usersRepo.save(users);
    }

    @Override
    public Optional<Users> findByUsername(String username) {

        return this.usersRepo.findByUsername(username);
    }
}
