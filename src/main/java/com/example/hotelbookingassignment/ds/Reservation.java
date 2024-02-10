package com.example.hotelbookingassignment.ds;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id", "reservationDate"})})
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    private Guest guest;
    private LocalDate reservationDate;

    public Reservation(Room room, Guest guest, LocalDate reservationDate) {
        this.room = room;
        this.guest = guest;
        this.reservationDate = reservationDate;
    }

    public Reservation(UUID id, Room room, Guest guest, LocalDate reservationDate) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.reservationDate = reservationDate;
    }
}
