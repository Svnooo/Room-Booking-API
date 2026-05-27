package com.mayora.meetingroom.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookBundleResponse {

    private Long id;
    private Long roomId;
    private String bookId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
