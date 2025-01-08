package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class ApartmentResponse {

    private Long id;
    private String name;
    private Integer number;
    private Long floorId;
    private Long buildingId;
    private Double area;
}
