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
public class Entrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Идентификатор подъезда

    private String entranceName;  // Название подъезда, например "Подъезд 1"

    @ManyToOne  // Связь с сущностью Building (многие к одному)
    @JoinColumn(name = "building_id", nullable = false)  // Столбец для хранения внешнего ключа на Building
    private Building building;  // Здание, к которому относится подъезд

    @OneToMany(mappedBy = "entrance", cascade = CascadeType.ALL)  // Связь с сущностью Floor (один ко многим)
    private List<Floor> floors;  // Список этажей, относящихся к подъезду
}
