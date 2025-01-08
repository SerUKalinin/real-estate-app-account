package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.ApartmentRequest;
import com.example.real_estate_app_account.dto.ApartmentResponse;
import com.example.real_estate_app_account.model.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(target = "floorId", source = "floor.id")
    @Mapping(target = "buildingId", source = "building.id")
    ApartmentResponse toResponse(Apartment apartment);

    Apartment toEntity(ApartmentRequest request);
}
