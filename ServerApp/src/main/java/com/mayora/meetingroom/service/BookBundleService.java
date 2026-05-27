package com.mayora.meetingroom.service;

import java.util.List;

import com.mayora.meetingroom.domain.entity.BookBundle;

public interface BookBundleService {

    List<BookBundle> getAllBookBundles();

    List<BookBundle> createBookBundles(Long roomId, List<String> bookIds);

    void deleteBookBundles(Long roomId, List<String> bookIds);

    BookBundle getBookBundleById(Long id);

    List<BookBundle> getBookBundlesByRoomId(Long roomId);

    List<BookBundle> getBookBundlesByBookId(String bookId);

    List<BookBundle> getBookBundlesByRoomIdAndBookId(Long roomId, String bookId);
}
