package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class FloorResponse {
    private Long id;  // ID этажа
    private int floorNumber;  // Номер этажа
    private Long buildingId;  // ID здания
    private String buildingName;  // Название здания
    private Long entranceId;  // ID подъезда
    private String entranceName;  // Название подъезда
}
