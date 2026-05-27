package com.mayora.meetingroom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayora.meetingroom.domain.entity.BookBundle;
import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.exception.NotFoundException;
import com.mayora.meetingroom.repository.BookBundleRepository;
import com.mayora.meetingroom.repository.RoomRepository;
import com.mayora.meetingroom.service.BookBundleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookBundleServiceImpl implements BookBundleService {

    private final BookBundleRepository bookBundleRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<BookBundle> getAllBookBundles() {
        return bookBundleRepository.findAll();
    }

    @Override
    @Transactional
    public List<BookBundle> createBookBundles(Long roomId, List<String> bookIds) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found"));

        List<BookBundle> created = new ArrayList<>();
        for (String bookId : bookIds) {
            BookBundle bundle = new BookBundle();
            bundle.setRoom(room);
            bundle.setBookId(bookId);
            created.add(bookBundleRepository.save(bundle));
        }
        return created;
    }

    @Override
    @Transactional
    public void deleteBookBundles(Long roomId, List<String> bookIds) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room not found");
        }

        List<BookBundle> toDelete = bookBundleRepository.findByRoomIdAndBookIdIn(roomId, bookIds);
        if (!toDelete.isEmpty()) {
            bookBundleRepository.deleteAll(toDelete);
        }
    }

    @Override
    public BookBundle getBookBundleById(Long id) {
        return bookBundleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book bundle not found"));
    }

    @Override
    public List<BookBundle> getBookBundlesByRoomId(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room not found");
        }
        return bookBundleRepository.findByRoomId(roomId);
    }

    @Override
    public List<BookBundle> getBookBundlesByBookId(String bookId) {
        return bookBundleRepository.findByBookId(bookId);
    }

    @Override
    public List<BookBundle> getBookBundlesByRoomIdAndBookId(Long roomId, String bookId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException("Room not found");
        }
        return bookBundleRepository.findByRoomIdAndBookId(roomId, bookId);
    }
}
