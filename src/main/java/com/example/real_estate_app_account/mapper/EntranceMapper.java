package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.model.Entrance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntranceMapper {

    @Mapping(source = "building.id", target = "buildingId")  // Преобразуем объект Building в его ID
    @Mapping(source = "building.name", target = "buildingName")  // Преобразуем имя Building в поле DTO
    EntranceResponse toEntranceResponse(Entrance entrance);

    @Mapping(source = "buildingId", target = "building.id")  // Преобразуем ID здания в объект Building
    Entrance toEntrance(EntranceResponse entranceResponse);
}
