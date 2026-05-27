package com.mayora.meetingroom.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookBundleRequest {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotEmpty(message = "Book ID list cannot be empty")
    private List<String> bookId;
}
