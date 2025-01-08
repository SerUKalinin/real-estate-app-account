package com.example.real_estate_app_account.dto;

import lombok.Data;

@Data
public class LobbyRequest {
    private String lobbyName;
    private Long elevatorId;
    private Long entranceId;
    private Integer floor;
    private Long buildingId;
}
