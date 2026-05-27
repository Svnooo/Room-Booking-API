package com.mayora.meetingroom.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mayora.meetingroom.domain.entity.BookBundle;
import com.mayora.meetingroom.dto.response.BookBundleResponse;

@Mapper(componentModel = "spring")
public interface BookBundleMapper {

    @Mapping(source = "room.id", target = "roomId")
    BookBundleResponse toResponse(BookBundle bookBundle);

    List<BookBundleResponse> toResponseList(List<BookBundle> bookBundles);
}
