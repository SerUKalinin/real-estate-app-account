package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class FloorRequest {
    private int floorNumber; // Номер этажа
    private Long buildingId; // ID здания
    private Long entranceId; // ID подъезда
}
