package com.example.hotelbookingassignment.repository;

import com.example.hotelbookingassignment.ds.Guest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends CrudRepository<Guest, UUID> {
    Guest findGuestByFirstNameAndLastName(String firstName, String lastName);
}
