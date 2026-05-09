package com.mayora.meetingroom.dto.response;

import lombok.Data;

@Data
public class RoomResponse {

    private Long id;
    private String name;
    private Integer capacity;
    private String location;
    private Boolean isAvailable;
}
