package com.example.real_estate_app_account.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Сущность Address для хранения данных об адресе.
 * Используется для хранения информации о местоположении зданий или других объектов недвижимости.
 */
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Идентификатор адреса

    private String street;  // Улица
    private String city;    // Город
    private String state;   // Штат/регион
    private String numberHouse;
}// Номер дома
