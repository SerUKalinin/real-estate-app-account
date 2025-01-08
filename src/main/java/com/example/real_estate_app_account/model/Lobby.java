package com.example.real_estate_app_account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lobbyName;

    @ManyToOne
    @JoinColumn(name = "elevator_id")
    private Elevator elevator;

    @ManyToOne
    @JoinColumn(name = "entrance_id")
    private Entrance entrance;

    private Integer floor;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}
