package com.example.real_estate_app_account.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Идентификатор здания

    private String name;  // Название здания (например, "Жилой комплекс 1")

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;  // Адрес здания

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Entrance, каскадирование всех операций, ленивый fetch
    private List<Entrance> entrances;  // Список подъездов в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Lobby, каскадирование всех операций, ленивый fetch
    private List<Lobby> lobbies;  // Список холлов в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Elevator, каскадирование всех операций, ленивый fetch
    private List<Elevator> elevators;  // Список лифтов в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Floor, каскадирование всех операций, ленивый fetch
    private List<Floor> floors;  // Список этажей в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Corridor, каскадирование всех операций, ленивый fetch
    private List<Corridor> corridors;  // Список коридоров в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Apartment, каскадирование всех операций, ленивый fetch
    private List<Apartment> apartments;  // Список квартир в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Связь один ко многим с сущностью Room, каскадирование всех операций, ленивый fetch
    private List<Room> rooms;  // Список комнат в здании
}
