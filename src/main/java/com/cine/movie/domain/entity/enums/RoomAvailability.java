package com.cine.movie.domain.entity.enums;

import lombok.Getter;

@Getter
public enum RoomAvailability {
    OCCUPIED_ROOM(0, "When the room is occupied"),
    AVAILABLE_ROOM(1, "When the room is available");

    private final Integer code;
    private final String description;

    RoomAvailability(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
