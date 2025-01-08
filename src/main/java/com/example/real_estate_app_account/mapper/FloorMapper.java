package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.model.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FloorMapper {

    @Mapping(target = "buildingId", source = "building.id")
    @Mapping(target = "buildingName", source = "building.name")
    @Mapping(target = "entranceId", source = "entrance.id")
    @Mapping(target = "entranceName", source = "entrance.entranceName")
    FloorResponse toResponse(Floor floor);

    Floor toEntity(FloorRequest request);
}
