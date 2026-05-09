package com.mayora.meetingroom.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}