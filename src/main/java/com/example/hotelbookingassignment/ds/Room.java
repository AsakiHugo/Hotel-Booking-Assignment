package com.example.hotelbookingassignment.ds;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Room {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String name;

    private String section;

    public Room(UUID id, String name, String section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }
}
