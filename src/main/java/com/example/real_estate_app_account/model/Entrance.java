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
public class Entrance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entranceName; // Название подъезда, например "Подъезд 1"

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building; // Здание, к которому относится подъезд

    @OneToMany(mappedBy = "entrance")
    private List<Floor> floors; // Этажи, относящиеся к подъезду
}
