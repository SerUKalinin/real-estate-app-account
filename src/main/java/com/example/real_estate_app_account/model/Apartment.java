package com.example.real_estate_app_account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Сущность Apartment для хранения данных о квартире.
 * Связана с сущностью Floor, указывая на этаж, на котором расположена квартира.
 */
@Data
@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Идентификатор квартиры

    private String apartmentNumber;  // Номер квартиры

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)  // Связь с сущностью Floor
    private Floor floor;  // Этаж, на котором находится квартира
}
