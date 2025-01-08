package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.CorridorRequest;
import com.example.real_estate_app_account.dto.CorridorResponse;
import com.example.real_estate_app_account.model.Corridor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CorridorMapper {

    @Mapping(source = "floor.id", target = "floorId")
    @Mapping(source = "building.id", target = "buildingId")
    CorridorResponse toResponse(Corridor corridor);

    @Mapping(source = "floorId", target = "floor.id")
    @Mapping(source = "buildingId", target = "building.id")
    Corridor toEntity(CorridorRequest request);
}
