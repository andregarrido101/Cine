package com.cine.movie.domain.repository;

import com.cine.movie.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /*
    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END\s
            FROM UserEntity u\s
            WHERE u.cpf = :cpf
            """)
    Boolean existsByCPF(String cpf);

     */
}
