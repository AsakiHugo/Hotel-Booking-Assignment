package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Role;
import com.example.hotelbookingassignment.repository.GuestRepository;
import com.example.hotelbookingassignment.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuestRegistrationService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Guest registerGuest(Guest guest) {
        var existingGuest = getExistingGuest(guest);

        if (existingGuest == null) {
            var newGuest = new Guest(
                    guest.getFirstName(),
                    guest.getLastName()
            );
            newGuest.setId(UUID.randomUUID());

            var role = roleRepository.findRoleByRoleName("ROLE_USER");
            newGuest.setRole(role);

            guestRepository.save(newGuest);
            existingGuest = getExistingGuest(newGuest);
        }

        return existingGuest;
    }

    private Guest getExistingGuest(Guest guest) {
        return guestRepository.findGuestByFirstNameAndLastName(guest.getFirstName(), guest.getLastName());
    }
}
