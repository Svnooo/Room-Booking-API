package com.mayora.meetingroom.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateBookingRequest {

    private Long userId;
    private Long roomId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String purpose;
}
