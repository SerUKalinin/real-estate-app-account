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

/**
 * Сущность Floor для хранения данных о этаже.
 * Связана с конкретным зданием, подъездом и квартирами на этом этаже.
 */
@Data
@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Идентификатор этажа

    private int floorNumber;  // Номер этажа (например, 1, 2, 3)

    @ManyToOne  // Связь с сущностью Building (многие к одному)
    @JoinColumn(name = "building_id", nullable = false)  // Столбец для хранения внешнего ключа на Building
    private Building building;  // Здание, к которому относится этаж

    @ManyToOne  // Связь с сущностью Entrance (многие к одному)
    @JoinColumn(name = "entrance_id", nullable = false)  // Столбец для хранения внешнего ключа на Entrance
    private Entrance entrance;  // Подъезд, к которому относится этаж

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)  // Связь с сущностью Apartment (один ко многим)
    private List<Apartment> apartments;  // Список квартир на этаже
}
