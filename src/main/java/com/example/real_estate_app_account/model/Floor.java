package com.example.real_estate_app_account.model;

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
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int floorNumber; // Номер этажа

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building; // Здание, к которому относится этаж

    @ManyToOne
    @JoinColumn(name = "entrance_id", nullable = false)
    private Entrance entrance; // Подъезд, к которому относится этаж

    @OneToMany(mappedBy = "floor")
    private List<Apartment> apartments; // Квартиры на этаже
}
