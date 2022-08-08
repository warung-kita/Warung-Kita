package com.pentagon.warungkita.controller;

import com.pentagon.warungkita.dto.JwtResponse;
import com.pentagon.warungkita.dto.LoginRequest;
import com.pentagon.warungkita.dto.SignupRequest;
import com.pentagon.warungkita.model.Users;
import com.pentagon.warungkita.security.jwt.JwtUtils;
import com.pentagon.warungkita.security.service.UserDetailsImpl;
import com.pentagon.warungkita.service.UsersService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Sign Up/Login")
@OpenAPIDefinition(info = @Info(title = "WarungKita",
        description = "Build by PENTAGON"))
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public Users signup(@RequestBody SignupRequest request) {
        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setEmail(request.getEmail());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setFullName(request.getNama());
        Users created = usersService.createUser(users);
        return created;
    }
}
