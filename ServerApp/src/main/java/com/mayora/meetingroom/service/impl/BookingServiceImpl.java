package com.mayora.meetingroom.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mayora.meetingroom.domain.entity.Booking;
import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.BookingStatus;
import com.mayora.meetingroom.domain.enums.Role;
import com.mayora.meetingroom.exception.BadRequestException;
import com.mayora.meetingroom.exception.NotFoundException;
import com.mayora.meetingroom.repository.BookingRepository;
import com.mayora.meetingroom.repository.RoomRepository;
import com.mayora.meetingroom.repository.UserRepository;
import com.mayora.meetingroom.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    // =============================
    // CREATE BOOKING
    // =============================

    @Override
    public Booking createBooking(
            Long userId,
            Long roomId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String purpose) {

        validateTimeRange(startTime, endTime);

        User user = findUserOrThrow(userId);
        Room room = findRoomOrThrow(roomId);

        ensureNoConflict(roomId, startTime, endTime);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setPurpose(purpose);
        booking.setCreatedAt(LocalDateTime.now());

        if (user.getRole() == Role.ADMIN) {
            booking.setStatus(BookingStatus.APPROVED);
            booking.setApprovedBy(user);
        } else {
            booking.setStatus(BookingStatus.PENDING);
        }

        return bookingRepository.save(booking);
    }

    // =============================
    // APPROVE BOOKING
    // =============================
    @Override
    public Booking approveBooking(Long bookingId, Long adminId) {

        Booking booking = findBookingOrThrow(bookingId);
        User admin = findUserOrThrow(adminId);

        ensureAdminRole(admin);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending booking can be approved");
        }

        // re-check conflict (CRITICAL)
        ensureNoConflict(
                booking.getRoom().getId(),
                booking.getStartTime(),
                booking.getEndTime());

        booking.setStatus(BookingStatus.APPROVED);
        booking.setApprovedBy(admin);

        return bookingRepository.save(booking);
    }

    // =============================
    // REJECT BOOKING
    // =============================
    @Override
    public Booking rejectBooking(Long bookingId, Long adminId) {

        Booking booking = findBookingOrThrow(bookingId);
        User admin = findUserOrThrow(adminId);

        ensureAdminRole(admin);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending booking can be rejected");
        }

        booking.setStatus(BookingStatus.REJECTED);
        booking.setApprovedBy(admin);

        return bookingRepository.save(booking);
    }

    // =============================
    // GET DATA
    // =============================
    @Override
    public Booking getBookingById(Long bookingId) {
        return findBookingOrThrow(bookingId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByRoom(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public List<Booking> getApprovedBookingsByRoom(Long roomId) {
        return bookingRepository.findByRoomIdAndStatus(roomId, BookingStatus.APPROVED);
    }

    // =============================
    // UPDATE BOOKING
    // =============================
    @Override
    public Booking updateBooking(
            Long bookingId,
            Long userId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String purpose) {

        Booking booking = findBookingOrThrow(bookingId);

        // only owner can update
        if (!booking.getUser().getId().equals(userId)) {
            throw new BadRequestException("You are not allowed to update this booking");
        }

        // only PENDING can be updated
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending booking can be updated");
        }

        validateTimeRange(startTime, endTime);

        ensureNoConflictExcludeSelf(
                bookingId,
                booking.getRoom().getId(),
                startTime,
                endTime);

        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setPurpose(purpose);

        return bookingRepository.save(booking);
    }

    // =============================
    // CANCEL BOOKING
    // =============================
    @Override
    public Booking cancelBooking(Long bookingId, Long userId) {

        Booking booking = findBookingOrThrow(bookingId);

        // prevent double cancel
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking already cancelled");
        }

        // owner or admin
        if (!booking.getUser().getId().equals(userId)) {
            User user = findUserOrThrow(userId);

            if (user.getRole() != Role.ADMIN) {
                throw new BadRequestException("Not allowed to cancel this booking");
            }
        }

        booking.setStatus(BookingStatus.CANCELLED);

        return bookingRepository.save(booking);
    }

    // =============================
    // PRIVATE METHODS (CORE LOGIC)
    // =============================

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null) {
            throw new BadRequestException("Start time and end time must not be null");
        }

        if (!start.isBefore(end)) {
            throw new BadRequestException("Start time must be before end time");
        }

        if (start.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Cannot book in the past");
        }
    }

    private void ensureNoConflict(Long roomId, LocalDateTime start, LocalDateTime end) {

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                roomId, start, end, BookingStatus.APPROVED);

        if (!conflicts.isEmpty()) {
            throw new BadRequestException("Room already booked for this time");
        }
    }

    private void ensureNoConflictExcludeSelf(
            Long bookingId,
            Long roomId,
            LocalDateTime start,
            LocalDateTime end) {

        List<Booking> conflicts = bookingRepository.findConflictingBookingsExcludeSelf(
                roomId, start, end, BookingStatus.APPROVED, bookingId);

        if (!conflicts.isEmpty()) {
            throw new BadRequestException("Room already booked for this time");
        }
    }

    private void ensureAdminRole(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new BadRequestException("Only admin can perform this action");
        }
    }

    private Booking buildBooking(User user, Room room,
            LocalDateTime start, LocalDateTime end,
            String purpose) {

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(start);
        booking.setEndTime(end);
        booking.setPurpose(purpose);
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        return booking;
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Room findRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }

    private Booking findBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }
}