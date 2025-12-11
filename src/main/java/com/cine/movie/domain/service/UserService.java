package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.users.UserCreateRequestDTO;
import com.cine.movie.domain.mapper.UserMapper;
import com.cine.movie.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final String EXISTING_USER = "User with %s [%s] already registered in our system.";

    public void registerUser(UserCreateRequestDTO dto) {
        verify(dto);
        var entity = mapper.userCreateRequestConvertDTOToEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.password()));
        repository.save(entity);
    }

    private void verify(UserCreateRequestDTO dto) {
        var verifyUserExistenceByEmail = repository.existsByEmail(dto.email());

        if (verifyUserExistenceByEmail) {
            throw new RuntimeException(String.format(EXISTING_USER, "Email", dto.email()));
        }
    }
}
