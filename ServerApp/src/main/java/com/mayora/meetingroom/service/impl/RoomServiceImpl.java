package com.mayora.meetingroom.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.exception.NotFoundException;
import com.mayora.meetingroom.repository.RoomRepository;
import com.mayora.meetingroom.service.RoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom(String name, Integer capacity, String location) {

        Room room = new Room();
        room.setName(name);
        room.setCapacity(capacity);
        room.setLocation(location);
        room.setIsAvailable(true);

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, String name, Integer capacity, String location) {

        Room room = findRoomOrThrow(id);

        room.setName(name);
        room.setCapacity(capacity);
        room.setLocation(location);

        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = findRoomOrThrow(id);
        roomRepository.delete(room);
    }

    @Override
    public Room getRoomById(Long id) {
        return findRoomOrThrow(id);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }

    private Room findRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }
}
