package com.cine.movie.domain.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
        name = "UserResponse",
        description = "Data of a user"
)
public record UserResponseDTO(

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
    String email
){
}
