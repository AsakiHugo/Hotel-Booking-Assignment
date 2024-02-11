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


@Service
public class ApplicationService {

    @Autowired
    private GuestRegistrationService guestRegistrationService;

    @Autowired
    private BookingService bookingService;

    public Guest registerGuest(String firstName, String lastName) {
        return guestRegistrationService.registerGuest(new Guest(firstName, lastName));
    }

    public BookingResult bookAnyRoomForNewGuest(String firstName, String lastName, LocalDate date) {

        // firstName + lastName => Guest
        var guest = registerGuest(firstName, lastName);

        // date => Room
        Room room = null;
        Optional<Room> tempRoom = bookingService.findAvailableRoom(date);

        if (tempRoom.isPresent()) {
            room = tempRoom.get();
        }

        // Guest + Room + date => Reservation
        Reservation reservation = null;
        Optional<Reservation> tempReservation = bookingService.bookRoom(room, guest, date);

        // Reservation => BookingResult
        BookingResult bookingResult = null;

        if (tempReservation.isPresent()) {
            reservation = tempReservation.get();
            bookingResult = BookingResult.createRoomBookedResult(reservation);
        } else {
            bookingResult = BookingResult.createNoRoomAvailableResult();
        }

        return bookingResult;
    }

    public BookingResult bookAnyRoomForRegisteredGuest(Guest guest, LocalDate date) {
        return null;
    }

    public BookingResult bookSpecificRoomForRegisteredGuest(Guest guest, String roomName, LocalDate date) {
       return null;
    }
}
