package com.cine.movie.domain.repository;

import com.cine.movie.domain.dto.users.UserResponseDTO;
import com.cine.movie.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            SELECT username, email
            FROM UserEntity
            WHERE id = :id
            """
    )
    Optional<UserResponseDTO> findUser(@Param("id") Long id);
}
