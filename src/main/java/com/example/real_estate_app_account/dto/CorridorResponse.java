package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class CorridorResponse {
    private Long id;
    private String name;
    private Long floorId;
    private Long buildingId;
}
