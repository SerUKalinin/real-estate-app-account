package com.example.real_estate_app_account.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    private Long id;

    private String name; // Например, "Жилой комплекс 1"

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address; // Адрес здания

    @OneToMany(mappedBy = "building")
    private List<Floor> floors; // Этажи в здании

    @OneToMany(mappedBy = "building")
    private List<Entrance> entrances; // Подъезды в здании
}
