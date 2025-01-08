package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.BuildingResponse;
import com.example.real_estate_app_account.model.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    @Mapping(source = "address.id", target = "addressId")  // Преобразуем адрес в его ID
    BuildingResponse toBuildingResponse(Building building);

    @Mapping(source = "addressId", target = "address.id")  // Преобразуем ID адреса в объект Address
    Building toBuilding(BuildingResponse buildingResponse);
}
