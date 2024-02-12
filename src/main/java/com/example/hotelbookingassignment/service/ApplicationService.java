package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.BookingResult;
import com.example.hotelbookingassignment.ds.Guest;


import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


@Service
public class ApplicationService {

    @Autowired
    private GuestRegistrationService guestRegistrationService;

    @Autowired
    private BookingService bookingService;

    public Set<Room> findAll() {
        return bookingService.findAll();
    }

    public Guest registerGuest(String firstName, String lastName) {
        return guestRegistrationService.registerGuest(new Guest(firstName, lastName));
    }

    public BookingResult bookAnyRoomForNewGuest(String firstName, String lastName, LocalDate date) {

        // firstName + lastName => Guest
        var guest = registerGuest(firstName, lastName);

        // date => Room
        Room room = null;
        Optional<Room> tempRoom = bookingService.findAvailableRoom(date);

        // Reservation => BookingResult
        BookingResult bookingResult = null;

        if (tempRoom.isPresent()) {
            room = tempRoom.get();

            // Guest + Room + date => Reservation
            Reservation reservation = bookingService.bookRoom(room, guest, date).get();
            bookingResult = BookingResult.createRoomBookedResult(reservation);

        } else {
            bookingResult = BookingResult.createNoRoomAvailableResult();
        }

        return bookingResult;
    }

    public BookingResult bookAnyRoomForRegisteredGuest(Guest guest, LocalDate date) {
        return bookAnyRoomForNewGuest(guest.getFirstName(), guest.getLastName(), date);
    }

    public BookingResult bookSpecificRoomForRegisteredGuest(Guest guest, String roomName, LocalDate date) {
        // guest => Guest
        var existingGuest = registerGuest(guest.getFirstName(), guest.getLastName());

        // Guest + roomName + date => Reservation
        var tempReservation = bookingService.bookRoom(roomName, existingGuest, date);

        // Reservation => BookingResult
        var bookingResult = BookingResult.createNoRoomAvailableResult();

        if (tempReservation.isPresent()) {
            bookingResult = BookingResult.createRoomBookedResult(tempReservation.get());
        }

        return bookingResult;
    }
}
