package com.example.hotelbookingassignment;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Role;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.repository.GuestRepository;
import com.example.hotelbookingassignment.repository.ReservationRepository;
import com.example.hotelbookingassignment.repository.RoleRepository;
import com.example.hotelbookingassignment.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class HotelBookingAssignmentApplication {
    private final GuestRepository guestRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingAssignmentApplication.class, args);
    }

    @Bean
    @Profile("data")
    public ApplicationRunner runner() {
        return r -> {
            var role1 = new Role("ROLE_ADMIN");
            var role2 = new Role("ROLE_USER");

            roleRepository.save(role1);
            roleRepository.save(role2);

            var guest1 = new Guest(UUID.randomUUID(), "Annie", "Rose", "12345");
            guest1.setRole(role2);

            var guest2 = new Guest(UUID.randomUUID(), "Asaki", "Hugo", "12345");
            guest2.setRole(role2);

            var guest3 = new Guest(UUID.randomUUID(), "Albert", "Myo", "12345");
            guest3.setRole(role2);

            guestRepository.save(guest1);
            guestRepository.save(guest2);
            guestRepository.save(guest3);

            Room room1 = new Room(UUID.randomUUID(), "HSD-101", "Standard");
            Room room2 = new Room(UUID.randomUUID(), "HOC-102", "Ocean View");
            Room room3 = new Room(UUID.randomUUID(), "HFS-103", "Family Suite");
            Room room4 = new Room(UUID.randomUUID(), "HSU-104", "Suite");
            Room room5 = new Room(UUID.randomUUID(), "HAC-105", "Accessible");
            Room room6 = new Room(UUID.randomUUID(), "HPH-106", "Penthouse");

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            roomRepository.save(room4);
            roomRepository.save(room5);
            roomRepository.save(room6);

            Reservation reservation = new Reservation(UUID.randomUUID(), room1, guest1, LocalDate.parse("2024-02-11"));

            reservationRepository.save(reservation);
        };
    }
}
