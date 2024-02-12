package com.example.hotelbookingassignment.controller;

import com.example.hotelbookingassignment.ds.BookingResult;
import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ApplicationService applicationService;

    @GetMapping(value = "/reservation/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Room>> getAllRooms() {
        return ResponseEntity.status(HttpStatus.FOUND).body(applicationService.findAll());
    }

    @PostMapping(value = "/reservation/random/{firstName}/{lastName}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResult> reservationForNewGuest(@PathVariable("firstName") String firstName,
                                                                @PathVariable("lastName") String lastName,
                                                                @PathVariable("date") LocalDate date) {
        var bookingResult = applicationService
                .bookAnyRoomForNewGuest(firstName, lastName, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResult);
    }

    @PostMapping(value = "/reservation/random/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResult> reservationForRegisteredGuest(@RequestBody Guest guest,
                                                                       @PathVariable("date") LocalDate date) {
        var bookingResult = applicationService
                .bookAnyRoomForRegisteredGuest(
                        new Guest(guest.getFirstName(), guest.getLastName()), date
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResult);
    }

    @PostMapping(value = "/reservation/specific/{roomName}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingResult> reservationSpecificRoomForGuest(@RequestBody Guest guest,
                                                                         @PathVariable("roomName") String roomName,
                                                                         @PathVariable("date") LocalDate date) {
        var bookingResult = applicationService
                .bookSpecificRoomForRegisteredGuest(guest, roomName, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResult);
    }
}
