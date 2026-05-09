package com.mayora.meetingroom.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mayora.meetingroom.domain.entity.Booking;

public interface BookingService {

    Booking createBooking(
            Long userId,
            Long roomId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String purpose);

    Booking approveBooking(Long bookingId, Long adminId);

    Booking rejectBooking(Long bookingId, Long adminId);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    List<Booking> getBookingsByUser(Long userId);

    List<Booking> getBookingsByRoom(Long roomId);

    List<Booking> getApprovedBookingsByRoom(Long roomId);

    Booking updateBooking(
            Long bookingId,
            Long userId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String purpose);

    Booking cancelBooking(Long bookingId, Long userId);
}
