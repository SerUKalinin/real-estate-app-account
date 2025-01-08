package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class LobbyResponse {
    private Long id;
    private String lobbyName;
    private Long elevatorId;
    private Long entranceId;
    private Integer floor;
    private Long buildingId;
}
