package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.BookingResult;
import com.example.hotelbookingassignment.ds.Guest;


import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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

        if (bookingService.findAvailableRoom(date).isPresent()) {
            room = bookingService.findAvailableRoom(date).get();
        }

        // Guest + Room + date => Reservation
        Reservation reservation = null;

        // Reservation => BookingResult
        BookingResult bookingResult = null;

        if (bookingService.bookRoom(room, guest, date).isPresent()) {
            reservation = bookingService.bookRoom(room, guest, date).get();
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
