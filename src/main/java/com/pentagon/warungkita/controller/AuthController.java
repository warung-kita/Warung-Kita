package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.*;
import com.pentagon.warungkita.exception.ResourceNotFoundException;
import com.pentagon.warungkita.model.Roles;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.repository.UsersRepo;
import com.pentagon.warungkita.response.ResponseHandler;
import com.pentagon.warungkita.security.jwt.JwtUtils;
import com.pentagon.warungkita.security.service.AuthServiceImpl;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.UsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@OpenAPIDefinition(info = @Info(title = "WarungKita",
        description = "Build by PENTAGON"))
@Tag(name ="01.Sign Up/Login")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UsersService usersService;;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthServiceImpl authServiceImpl;
    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {


        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Optional <Users> user = usersRepo.findByUsername(request.getUsername());
        if(user.get().isActive() == false){
            throw new ResourceNotFoundException("Username is not active, please contact admin.");
        }
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Users> users = usersService.findByUsername(principal.getUsername());
        List<Roles> roles = users.get().getRoles();
        List<String> stringsrole = new ArrayList<>();
        roles.forEach(roles1 -> {
            stringsrole.add(roles1.getName());
        });
        return ResponseEntity.ok().body(new JwtResponse(token, principal.getUsername(), principal.getPassword(), stringsrole));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest request) {
        return authServiceImpl.signup(request);
    }


//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) throws ResourceAlreadyExistException {
//        return authServiceImpl.registerUser(signupRequest);
//    }
}
