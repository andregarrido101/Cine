package com.cine.movie.domain.dto.http;

public record ClientResponseDTO(
        String status,
        DataResponseDTO data
) {
}
