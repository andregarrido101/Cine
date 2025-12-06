package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.IUserController;
import com.cine.movie.domain.dto.users.UserCreateRequestDTO;
import com.cine.movie.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final UserService service;

    @Override
    public ResponseEntity<Void> registrar(UserCreateRequestDTO userCreateRequestDTO) {
        service.registerUser(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
