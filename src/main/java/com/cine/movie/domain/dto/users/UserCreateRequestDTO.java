package com.cine.movie.domain.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
        name = "UserCreateRequest",
        description = "Data required to create a new user in the system."
)
public record UserCreateRequestDTO(

        @Schema(
                description = "User's full name",
                example = "João da Silva",
                requiredMode = REQUIRED,
                minLength = 3,
                maxLength = 255
        )
        @NotBlank
        @Size(min = 3, max = 255)
        String username,

        @Schema(
                description = "User E-mail",
                example = "joao.silva@email.com",
                requiredMode = REQUIRED,
                minLength = 8,
                maxLength = 254
        )
        @NotBlank
        @Email
        @Size(min = 8, max = 254)
        String email,

        @Schema(
                description = "User password",
                example = "securepassword123",
                requiredMode = REQUIRED,
                minLength = 11,
                maxLength = 255
        )
        @NotBlank
        @Size(min = 11, max = 255)
        String password
) {
}