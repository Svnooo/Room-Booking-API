package com.mayora.meetingroom.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mayora.meetingroom.domain.entity.Booking;
import com.mayora.meetingroom.domain.enums.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        SELECT b FROM Booking b
        WHERE b.room.id = :roomId
        AND b.status = :status
        AND (
            (:startTime < b.endTime) AND (:endTime > b.startTime)
        )
    """)
    List<Booking> findConflictingBookings(
        @Param("roomId") Long roomId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("status") BookingStatus status
    );

    @Query("""
        SELECT b FROM Booking b
        WHERE b.room.id = :roomId
        AND b.status = :status
        AND b.id != :bookingId
        AND (
            (:startTime < b.endTime) AND (:endTime > b.startTime)
        )
    """)
    List<Booking> findConflictingBookingsExcludeSelf(
        @Param("roomId") Long roomId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime,
        @Param("status") BookingStatus status,
        @Param("bookingId") Long bookingId
    );

    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByRoomIdAndStatus(Long roomId, BookingStatus status);
}
