package com.mayora.meetingroom.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mayora.meetingroom.domain.entity.Booking;
import com.mayora.meetingroom.dto.response.BookingResponse;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "status", target = "status")
    BookingResponse toResponse(Booking booking);

    List<BookingResponse> toResponseList(List<Booking> bookings);
}
