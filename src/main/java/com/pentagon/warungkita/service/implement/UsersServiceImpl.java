package com.pentagon.warungkita.service.implement;

import com.pentagon.warungkita.controller.UsersController;
import com.pentagon.warungkita.dto.PassworRequest;
import com.pentagon.warungkita.dto.UsersRequestDTO;
import com.pentagon.warungkita.dto.UsersResponseDTO;
import com.pentagon.warungkita.dto.UsersResponsePOST;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.RolesRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger(UsersController.class);

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
//        users.setActive(true);
//        users.setPassword(passwordEncoder.encode(users.getPassword()));
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

    @Override
    public ResponseEntity<Object> createUser(UsersRequestDTO usersRequestDTO) {
        try {
            if (usersRepo.existsByUsername(usersRequestDTO.getUsername())) {
                throw new Exception("Username already taken!");
            }
            if (usersRepo.existsByEmail(usersRequestDTO.getEmail())) {
                throw new Exception("Email already in use!");
            }
            if(usersRequestDTO.getRoles() == null){
                Roles role = rolesRepo.findByName("ROLE_BUYER");
                List<Roles> roles = new ArrayList<>();
                roles.add(role);
                usersRequestDTO.setRoles(roles);
                usersRequestDTO.setActive(true);
            }
            usersRequestDTO.setPassword(passwordEncoder.encode(usersRequestDTO.getPassword()));
            Users users = usersRequestDTO.convertToEntity();
            usersRepo.save(users);
            UsersResponsePOST userResult = users.convertToResponsePOST();
            logger.info("==================== Logger Start Create New User ====================");
            logger.info(userResult);
            logger.info("==================== Logger End Create New User =================");
            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, userResult);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
        }
    }

    @Override
    public ResponseEntity<Object> changePassword(PassworRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users user1 = this.findById(userDetails.getUserId());
            if (!Objects.equals(request.getConfirmUsername(), userDetails.getUsername())){
                throw new ResourceNotFoundException("Username is wrong");
            }
            if (userDetails.getPassword().equals(request.getPassword())){
                throw new ResourceNotFoundException("Password is same");
            }
            if (!Objects.equals(request.getPassword(), request.getConfirmPassword())){
                throw new ResourceNotFoundException("Password is not match, try again");
            }
            user1.setPassword((passwordEncoder.encode(request.getPassword())));
            Users updateUsers = this.updateUser(user1);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Updated Password!",HttpStatus.OK, result);
        }catch(Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Bad Request!!");
        }
    }

    @Override
    public ResponseEntity<Object> completeUsers(UsersRequestDTO usersRequestDTO) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Users> user1 = usersRepo.findById(userDetails.getUserId());
            Users users = usersRequestDTO.convertToEntity();
            users.setUserId(userDetails.getUserId());
            users.setEmail(userDetails.getEmail());
            users.setPassword(userDetails.getPassword());
            users.setUsername(userDetails.getUsername());
            users.setFullName(user1.get().getFullName());
            users.setRoles(user1.get().getRoles());

            Users updateUsers = this.updateUser(users);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Updated User!",HttpStatus.OK, result);
        }catch(Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Bad Request!!");
        }
    }

    @Override
    public ResponseEntity<Object> becameSeller() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users users = this.findById(userDetails.getUserId());
            users.setUserId(userDetails.getUserId());
            Roles role1 = rolesRepo.findByName("ROLE_SELLER");
            Roles role2 = rolesRepo.findByName("ROLE_BUYER");
            List<Roles> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);
            users.setRoles(roles);
            users.setActive(true);
            Users updateUsers = this.updateUser(users);
            UsersResponseDTO result = updateUsers.convertToResponse();
            logger.info("==================== Logger Start Update User By ID ====================");
            logger.info(result);
            logger.info("==================== Logger End Update User By ID =================");
            return ResponseHandler.generateResponse("Successfully Open Shop", HttpStatus.CREATED, result);
        }catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request!!");
        }

    }
}
