package com.example.hotelbookingassignment.controller;

import com.example.hotelbookingassignment.ds.BookingResult;
import com.example.hotelbookingassignment.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ApplicationService applicationService;

    @PostMapping("/reservation/random")
    @ResponseBody
    public ResponseEntity<BookingResult> reservation(String firstName, String lastName, LocalDate date) {
        var bookingResult = applicationService.bookAnyRoomForNewGuest(firstName, lastName, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResult);
    }
}
