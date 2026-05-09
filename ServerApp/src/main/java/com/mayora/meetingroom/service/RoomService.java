package com.mayora.meetingroom.service;

import java.util.List;

import com.mayora.meetingroom.domain.entity.Room;

public interface RoomService {

    Room createRoom(String name, Integer capacity, String location);

    Room updateRoom(Long id, String name, Integer capacity, String location);

    void deleteRoom(Long id);

    Room getRoomById(Long id);

    List<Room> getAllRooms();

    List<Room> getAvailableRooms();
}
