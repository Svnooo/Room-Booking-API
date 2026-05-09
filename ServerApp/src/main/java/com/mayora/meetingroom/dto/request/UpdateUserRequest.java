package com.mayora.meetingroom.dto.request;

import com.mayora.meetingroom.domain.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String name;

    @NotNull
    private Role role;

    @NotNull
    private Boolean isActive;
}
