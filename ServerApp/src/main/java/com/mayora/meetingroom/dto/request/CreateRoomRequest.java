package com.mayora.meetingroom.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoomRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotBlank
    private String location;
}
