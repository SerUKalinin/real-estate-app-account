package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class CorridorRequest {
    private String name;
    private Long floorId;
    private Long buildingId;
}
