package com.mayora.meetingroom.config;


import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mayora.meetingroom.domain.entity.Room;
import com.mayora.meetingroom.domain.entity.User;
import com.mayora.meetingroom.domain.enums.Role;
import com.mayora.meetingroom.repository.RoomRepository;
import com.mayora.meetingroom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder encoder;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {

            // =============================
            // ADMIN
            // =============================
            if (userRepository.findByEmail("admin@mail.com").isEmpty()) {
                userRepository.save(new User(
                        null,
                        "Admin",
                        "admin@mail.com",
                        encoder.encode("admin123"),
                        Role.ADMIN,
                        true,
                        LocalDateTime.now()
                ));
            }

            // =============================
            // USERS
            // =============================
            if (userRepository.count() <= 1) {

                userRepository.save(new User(
                        null,
                        "Ariel",
                        "ariel@gmail.com",
                        encoder.encode("123456"),
                        Role.USER,
                        true,
                        LocalDateTime.now()
                ));

                userRepository.save(new User(
                        null,
                        "Budi",
                        "budi@gmail.com",
                        encoder.encode("123456"),
                        Role.USER,
                        true,
                        LocalDateTime.now()
                ));
            }

            // =============================
            // ROOMS
            // =============================
            if (roomRepository.count() == 0) {

                roomRepository.save(new Room(
                        null,
                        "Meeting Room A",
                        10,
                        "Floor 1",
                        true,
                        LocalDateTime.now()
                ));

                roomRepository.save(new Room(
                        null,
                        "Meeting Room B",
                        20,
                        "Floor 2",
                        true,
                        LocalDateTime.now()
                ));
            }
        };
    }
}
