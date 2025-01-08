package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class ElevatorResponse {
    private Long id;
    private String name; // Название лифта
    private Long entranceId; // ID подъезда
    private Integer floor; // Этаж
    private Long buildingId;
}
