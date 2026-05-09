package com.mayora.meetingroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.dto.request.CreateRoomRequest;
import com.mayora.meetingroom.dto.response.RoomResponse;
import com.mayora.meetingroom.mapper.RoomMapper;
import com.mayora.meetingroom.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    // =============================
    // CREATE (ADMIN)
    // =============================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody CreateRoomRequest request) {

        Room room = roomService.createRoom(
                request.getName(),
                request.getCapacity(),
                request.getLocation()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.toResponse(room));
    }

    // =============================
    // GET ALL
    // =============================
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(
                roomMapper.toResponseList(roomService.getAllRooms())
        );
    }

    // =============================
    // GET AVAILABLE
    // =============================
    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() {
        return ResponseEntity.ok(
                roomMapper.toResponseList(roomService.getAvailableRooms())
        );
    }

    // =============================
    // GET BY ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(
                roomMapper.toResponse(roomService.getRoomById(id))
        );
    }

    // =============================
    // UPDATE (ADMIN)
    // =============================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody CreateRoomRequest request) {

        Room room = roomService.updateRoom(
                id,
                request.getName(),
                request.getCapacity(),
                request.getLocation()
        );

        return ResponseEntity.ok(roomMapper.toResponse(room));
    }

    // =============================
    // DELETE (ADMIN)
    // =============================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }
}
