package com.mayora.meetingroom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayora.meetingroom.dto.request.BookBundleRequest;
import com.mayora.meetingroom.dto.response.BookBundleResponse;
import com.mayora.meetingroom.dto.response.RoomResponse;
import com.mayora.meetingroom.mapper.BookBundleMapper;
import com.mayora.meetingroom.mapper.RoomMapper;
import com.mayora.meetingroom.service.BookBundleService;
import com.mayora.meetingroom.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final BookBundleService bookBundleService;
    private final BookBundleMapper bookBundleMapper;

    // =============================
    // GET ALL ROOMS (PUBLIC)
    // =============================
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(
                roomMapper.toResponseList(roomService.getAllRooms()));
    }

    // =============================
    // GET ROOM BY ID (PUBLIC)
    // =============================
    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(
                roomMapper.toResponse(roomService.getRoomById(id)));
    }

    // =============================
    // GET ALL BOOK BUNDLES (PUBLIC)
    // =============================
    @GetMapping("/book-bundle")
    public ResponseEntity<List<BookBundleResponse>> getAllBookBundles() {
        return ResponseEntity.ok(
                bookBundleMapper.toResponseList(bookBundleService.getAllBookBundles()));
    }

    // =============================
    // POST CREATE BATCH BOOK BUNDLES (PUBLIC)
    // =============================
    @PostMapping("/book-bundle")
    public ResponseEntity<List<BookBundleResponse>> createBookBundles(
            @Valid @RequestBody BookBundleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookBundleMapper.toResponseList(
                        bookBundleService.createBookBundles(request.getRoomId(), request.getBookId())));
    }

    // =============================
    // DELETE BATCH BOOK BUNDLES (PUBLIC)
    // =============================
    @DeleteMapping("/book-bundle")
    public ResponseEntity<Void> deleteBookBundles(
            @Valid @RequestBody BookBundleRequest request) {
        bookBundleService.deleteBookBundles(request.getRoomId(), request.getBookId());
        return ResponseEntity.noContent().build();
    }

    // =============================
    // GET BOOK BUNDLE DETAIL (PUBLIC)
    // =============================
    @GetMapping("/book-bundle/{id}")
    public ResponseEntity<BookBundleResponse> getBookBundle(@PathVariable Long id) {
        return ResponseEntity.ok(
                bookBundleMapper.toResponse(bookBundleService.getBookBundleById(id)));
    }

    // =============================
    // GET BOOK BUNDLES BY ROOM ID (PUBLIC)
    // =============================
    @GetMapping("/book-bundle/room/{roomId}")
    public ResponseEntity<List<BookBundleResponse>> getBookBundlesByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(
                bookBundleMapper.toResponseList(bookBundleService.getBookBundlesByRoomId(roomId)));
    }

    // =============================
    // GET BOOK BUNDLES BY BOOK ID (PUBLIC)
    // =============================
    @GetMapping("/book-bundle/book/{bookId}")
    public ResponseEntity<List<BookBundleResponse>> getBookBundlesByBook(@PathVariable String bookId) {
        return ResponseEntity.ok(
                bookBundleMapper.toResponseList(bookBundleService.getBookBundlesByBookId(bookId)));
    }

    // =============================
    // GET BOOK BUNDLES BY ROOM ID AND BOOK ID (PUBLIC)
    // =============================
    @GetMapping("/book-bundle/room/{roomId}/book/{bookId}")
    public ResponseEntity<List<BookBundleResponse>> getBookBundlesByRoomAndBook(
            @PathVariable Long roomId,
            @PathVariable String bookId) {
        return ResponseEntity.ok(
                bookBundleMapper.toResponseList(bookBundleService.getBookBundlesByRoomIdAndBookId(roomId, bookId)));
    }
}
