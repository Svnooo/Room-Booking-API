package com.mayora.meetingroom.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingResponse {

    private Long id;

    private String userName;
    private String roomName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String purpose;

    private String status;

    private LocalDateTime createdAt;
}
