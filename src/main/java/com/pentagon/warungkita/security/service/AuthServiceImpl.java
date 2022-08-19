package com.pentagon.warungkita.security.service;

import com.pentagon.warungkita.controller.AuthController;
import com.pentagon.warungkita.dto.SignupRequest;
import com.pentagon.warungkita.dto.UsersResponsePOST;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.RolesRepo;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.jwt.JwtUtils;
import com.pentagon.warungkita.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UsersService usersService;;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolesRepo roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Override
    public ResponseEntity<Object> signup(SignupRequest request){

        try {

            if (usersRepo.existsByUsername(request.getUsername())) {
                throw new Exception("Username already taken!");
            }
            if (usersRepo.existsByEmail(request.getEmail())) {
                throw new Exception("Email already in use!");
            }

            Users users = new Users();
            users.setUsername(request.getUsername());
            users.setEmail(request.getEmail());
            users.setPassword(request.getPassword());
            users.setFullName(request.getFullName());
            usersService.createUser(users);
            UsersResponsePOST userResult = users.convertToResponsePOST();
            logger.info("------------------------------------");
            logger.info("User created: " + userResult);
            logger.info("------------------------------------");
            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, userResult);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "User Already Exist!");
        }
    }
}