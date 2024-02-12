package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.repository.ReservationRepository;
import com.example.hotelbookingassignment.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Set<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findAvailableRoom(LocalDate date) {
        return roomRepository.findAvailableRoom(date);
    }

    @Transactional
    public Optional<Reservation> bookRoom(Room room, Guest guest, LocalDate date) {
        var reservation = new Reservation(room, guest, date);
        reservation.setId(UUID.randomUUID());
        reservationRepository.save(reservation);
        return reservationRepository.findById(reservation.getId());
    }

    public Optional<Reservation> bookRoom(String roomName, Guest guest, LocalDate date) {
        // roomName => Room
        var room = roomRepository.findByName(roomName)
                .orElseThrow(EntityNotFoundException::new);

        // Room + Guest + date => Reservation
        Optional<Reservation> reservation = Optional.empty();
        var hasReservation = isRoomAvailableAtDate(room, date);

        if (hasReservation == false) {
            reservation = bookRoom(room, guest, date);
        }

        return reservation;
    }

    private boolean isRoomAvailableAtDate(Room room, LocalDate date) {
        return reservationRepository.existsByRoomAndReservationDate(room, date);
    }
}
