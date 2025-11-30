package com.cine.movie.domain.mapper;

import com.cine.movie.domain.dto.users.UserCreateRequestDTO;
import com.cine.movie.domain.dto.users.UserResponseDTO;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserMapper {

    public UserEntity userCreateRequestConvertDTOToEntity(UserCreateRequestDTO dto) {
        if (isNull(dto)) return null;

        var userEntity = new UserEntity();
        userEntity.setUsername(dto.username());
        userEntity.setEmail(dto.email());
        return userEntity;
    }

    public UserEntity userResponseConvertDTOToEntity(UserResponseDTO dto) {
        if (isNull(dto)) return null;

        var userEntity = new UserEntity();
        userEntity.setUsername(dto.username());
        userEntity.setEmail(dto.email());
        return userEntity;
    }

    public UserResponseDTO userResponseConvertEntityToDTO(UserEntity user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail());
    }
}
