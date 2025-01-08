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
    private Long id;  // Идентификатор здания

    private String name;  // Название здания, например, "Жилой комплекс 1"

    @ManyToOne(cascade = CascadeType.ALL)  // Связь с сущностью Address (многие к одному)
    @JoinColumn(name = "address_id", nullable = false)  // Столбец для хранения внешнего ключа на Address
    private Address address;  // Адрес здания

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)  // Связь с сущностью Floor (один ко многим)
    private List<Floor> floors;  // Список этажей в здании

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)  // Связь с сущностью Entrance (один ко многим)
    private List<Entrance> entrances;  // Список подъездов в здании
}
