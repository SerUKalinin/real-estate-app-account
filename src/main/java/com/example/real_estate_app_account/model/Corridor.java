package com.example.real_estate_app_account.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Corridor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Floor floor;

    @ManyToOne
    private Building building;
}
