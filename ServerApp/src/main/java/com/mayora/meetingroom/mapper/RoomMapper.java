package com.mayora.meetingroom.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.dto.response.RoomResponse;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomResponse toResponse(Room room);

    List<RoomResponse> toResponseList(List<Room> rooms);
}
