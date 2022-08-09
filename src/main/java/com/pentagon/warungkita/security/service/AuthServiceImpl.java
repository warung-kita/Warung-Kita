//package com.pentagon.warungkita.security.service;
//
//import com.pentagon.warungkita.controller.AuthController;
//import com.pentagon.warungkita.dto.MessageResponse;
//import com.pentagon.warungkita.dto.SignupRequest;
//import com.pentagon.warungkita.exception.ResourceAlreadyExistException;
//import com.pentagon.warungkita.model.Users;
//import com.pentagon.warungkita.repository.RolesRepo;
//import com.pentagon.warungkita.repository.UsersRepo;
//import com.pentagon.warungkita.response.ResponseHandler;
//import com.pentagon.warungkita.security.jwt.JwtUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.ZonedDateTime;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    UsersRepo userRepository;
//
//    @Autowired
//    RolesRepo roleRepository;
//
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Value("${com.app.name}")
//    String projectName;
//
//    @Value("${com.app.team}")
//    String projectTeam;
//
//    private final HttpHeaders headers = new HttpHeaders();
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//    @Override
//    public ResponseEntity<?> registerUser(SignupRequest signupRequest) throws ResourceAlreadyExistException {
//        headers.set("APP-NAME", projectName + "-API " + projectTeam);
//
//        try {
//            if (userRepository.existsByUsername(signupRequest.getUsername())) {
//                throw new ResourceAlreadyExistException("Username already taken!");
//            }
//            if (userRepository.existsByEmailAddress(signupRequest.getEmail())) {
//                throw new ResourceAlreadyExistException("Email already in use!");
//            }
//
//            Users user = new Users(signupRequest.getFullnama(), signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
//
//            userRepository.save(user);
//            logger.info("--------------------------");
//            logger.info("Register User " + user);
//            logger.info("--------------------------");
//            return ResponseHandler.generateResponse("Successfully Create USER",HttpStatus.OK,user);
//
//        } catch (ResourceAlreadyExistException e) {
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed Create USER");
//        }
//    }
//}