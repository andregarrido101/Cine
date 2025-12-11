package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.IAuthController;
import com.cine.movie.domain.dto.auth.LoginRequestDTO;
import com.cine.movie.domain.dto.auth.LoginResponseDTO;
import com.cine.movie.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements IAuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        var response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}
