package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.LobbyResponse;
import com.example.real_estate_app_account.model.Lobby;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LobbyMapper {

    @Mapping(source = "elevator.id", target = "elevatorId")
    @Mapping(source = "entrance.id", target = "entranceId")
    @Mapping(source = "building.id", target = "buildingId")
    LobbyResponse toLobbyResponse(Lobby lobby);
}
