package com.mayora.meetingroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayora.meetingroom.domain.entity.BookBundle;

@Repository
public interface BookBundleRepository extends JpaRepository<BookBundle, Long> {

    List<BookBundle> findByRoomId(Long roomId);

    List<BookBundle> findByRoomIdAndBookIdIn(Long roomId, List<String> bookIds);

    List<BookBundle> findByBookId(String bookId);

    List<BookBundle> findByRoomIdAndBookId(Long roomId, String bookId);
}
