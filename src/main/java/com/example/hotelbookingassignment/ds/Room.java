package com.example.hotelbookingassignment.ds;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;

    private String section;

    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name, String section) {
        this.name = name;
        this.section = section;
    }

    public Room(UUID id, String name, String section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }

    public void addReservation(Reservation reservation) {
        reservation.setRoom(this);
        reservations.add(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(section, room.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, section);
    }
}
