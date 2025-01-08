package com.example.real_estate_app_account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double area;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "corridor_id", nullable = false)
    private Corridor corridor;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    @ManyToOne
    @JoinColumn(name = "entrance_id", nullable = false)
    private Entrance entrance;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
