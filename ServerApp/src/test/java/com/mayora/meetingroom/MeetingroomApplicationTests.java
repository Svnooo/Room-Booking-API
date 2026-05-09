package com.mayora.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mayora.meetingroom.mapper.BookingMapper;

@SpringBootTest
class MeetingroomApplicationTests {

    @MockBean
    private BookingMapper bookingMapper;

    @Test
    void contextLoads() {
    }
}
