package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.repository.ReservationRepository;
import com.example.hotelbookingassignment.repository.RoomRepository;
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

    public Optional<Room> findAvailableRoom(LocalDate date) {
        return roomRepository.findRoomByReservationDate(date);
    }

    public Optional<Reservation> bookRoom(String roomName, Guest guest, LocalDate date) {
        return null;
    }

    @Transactional
    public Optional<Reservation> bookRoom(Room room, Guest guest, LocalDate date) {
        var reservation = new Reservation(room, guest, date);
        reservation.setId(UUID.randomUUID());
        reservationRepository.save(reservation);
        return reservationRepository.findById(reservation.getId());
    }

    private boolean isRoomAvailableAtDate(Room room, LocalDate date) {
        return false;
    }
}
