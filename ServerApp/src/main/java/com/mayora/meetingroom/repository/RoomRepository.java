package com.mayora.meetingroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayora.meetingroom.domain.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Long>{
    
    List<Room> findByIsAvailableTrue();
}
