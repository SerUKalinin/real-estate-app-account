package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.ElevatorRequest;
import com.example.real_estate_app_account.dto.ElevatorResponse;
import com.example.real_estate_app_account.model.Elevator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ElevatorMapper {

    @Mapping(source = "entrance.id", target = "entranceId")
    ElevatorResponse toResponse(Elevator elevator);

    @Mapping(source = "entranceId", target = "entrance.id")
    Elevator toEntity(ElevatorRequest request);
}
