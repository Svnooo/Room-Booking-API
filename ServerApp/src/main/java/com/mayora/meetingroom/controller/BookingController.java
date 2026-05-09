package com.mayora.meetingroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.domain.entity.Booking;
import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.dto.request.CreateBookingRequest;
import com.mayora.meetingroom.dto.response.BookingResponse;
import com.mayora.meetingroom.mapper.BookingMapper;
import com.mayora.meetingroom.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

        private final BookingService bookingService;
        private final BookingMapper bookingMapper;

        // =============================
        // CREATE
        // =============================
        @PostMapping
        public ResponseEntity<BookingResponse> createBooking(
                        @AuthenticationPrincipal User user,
                        @Valid @RequestBody CreateBookingRequest request) {

                Booking booking = bookingService.createBooking(
                                user.getId(),
                                request.getRoomId(),
                                request.getStartTime(),
                                request.getEndTime(),
                                request.getPurpose());

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(bookingMapper.toResponse(booking));
        }

        @PatchMapping("/{id}/approve")
        public ResponseEntity<BookingResponse> approveBooking(
                        @PathVariable Long id,
                        @AuthenticationPrincipal User user) {

                Booking booking = bookingService.approveBooking(id, user.getId());

                return ResponseEntity.ok(bookingMapper.toResponse(booking));
        }

        @PatchMapping("/{id}/reject")
        public ResponseEntity<BookingResponse> rejectBooking(
                        @PathVariable Long id,
                        @AuthenticationPrincipal User user) {

                Booking booking = bookingService.rejectBooking(id, user.getId());

                return ResponseEntity.ok(bookingMapper.toResponse(booking));
        }

        @GetMapping
        public ResponseEntity<List<BookingResponse>> getBookings(
                        @AuthenticationPrincipal User user) {

                if (user == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                if (user.getRole().name().equals("ADMIN")) {
                        return ResponseEntity.ok(
                                        bookingMapper.toResponseList(bookingService.getAllBookings()));
                }

                return ResponseEntity.ok(
                                bookingMapper.toResponseList(
                                                bookingService.getBookingsByUser(user.getId())));
        }

        // =============================
        // GET BY ID
        // =============================
        @GetMapping("/{id}")
        public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
                return ResponseEntity.ok(
                                bookingMapper.toResponse(bookingService.getBookingById(id)));
        }

        // =============================
        // GET BY USER
        // =============================
        @GetMapping("/me")
        public ResponseEntity<List<BookingResponse>> getMyBookings(
                        @AuthenticationPrincipal User user) {

                return ResponseEntity.ok(
                                bookingMapper.toResponseList(
                                                bookingService.getBookingsByUser(user.getId())));
        }

        // =============================
        // GET BY ROOM
        // =============================
        @GetMapping("/room/{roomId}")
        public ResponseEntity<List<BookingResponse>> getBookingsByRoom(
                        @PathVariable Long roomId) {

                return ResponseEntity.ok(
                                bookingMapper.toResponseList(
                                                bookingService.getBookingsByRoom(roomId)));
        }

        // =============================
        // UPDATE
        // =============================
        @PutMapping("/{id}")
        public ResponseEntity<BookingResponse> updateBooking(
                        @PathVariable Long id,
                        @AuthenticationPrincipal User user,
                        @Valid @RequestBody CreateBookingRequest request) {

                Booking booking = bookingService.updateBooking(
                                id,
                                user.getId(),
                                request.getStartTime(),
                                request.getEndTime(),
                                request.getPurpose());

                return ResponseEntity.ok(bookingMapper.toResponse(booking));
        }

        // =============================
        // CANCEL
        // =============================
        @DeleteMapping("/{id}")
        public ResponseEntity<BookingResponse> cancelBooking(
                        @PathVariable Long id,
                        @AuthenticationPrincipal User user) {

                Booking booking = bookingService.cancelBooking(id, user.getId());

                return ResponseEntity.ok(bookingMapper.toResponse(booking));
        }

        
}