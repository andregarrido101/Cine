package com.cine.movie.domain.entity.enums;

import lombok.Getter;

@Getter
public enum MovieAvailability {
    UNAVAILABLE_MOVIE(0, "When the movie is not available for viewing"),
    AVAILABLE_MOVIE(1, "When the movie is available for viewing");

    private final Integer code;
    private final String description;

    MovieAvailability(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
