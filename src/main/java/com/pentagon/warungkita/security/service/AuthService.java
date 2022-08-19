package com.pentagon.warungkita.security.service;

import com.pentagon.warungkita.dto.SignupRequest;
import com.pentagon.warungkita.dto.UsersRequestDTO;
import com.pentagon.warungkita.exception.ResourceAlreadyExistException;
import com.pentagon.warungkita.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    ResponseEntity<Object> signup(@RequestBody SignupRequest request);
}
