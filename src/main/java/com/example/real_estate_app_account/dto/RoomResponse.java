package com.example.real_estate_app_account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponse {
    private Long id;
    private String name;
    private Double area;
    private Long apartmentId;
    private Long corridorId;
    private Long floorId;
    private Long entranceId;
    private Long buildingId;
}
