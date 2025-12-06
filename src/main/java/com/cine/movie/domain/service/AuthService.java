package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.auth.LoginRequestDTO;
import com.cine.movie.domain.dto.auth.LoginResponseDTO;
import com.cine.movie.domain.repository.UserRepository;
import com.cine.movie.security.TokenUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        var user = userRepository.findByEmail(dto.login())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        var authToken = tokenUtil.enodeToken(user);

        return new LoginResponseDTO(
                authToken.getToken(),
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
