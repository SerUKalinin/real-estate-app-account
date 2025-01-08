package com.example.real_estate_app_account.mapper;

import com.example.real_estate_app_account.dto.RoomRequest;
import com.example.real_estate_app_account.dto.RoomResponse;
import com.example.real_estate_app_account.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "corridorId", source = "corridor.id")
    @Mapping(target = "floorId", source = "floor.id")
    @Mapping(target = "entranceId", source = "entrance.id")
    @Mapping(target = "buildingId", source = "building.id")
    RoomResponse toResponse(Room room);

    @Mapping(target = "apartment", ignore = true)
    @Mapping(target = "corridor", ignore = true)
    @Mapping(target = "floor", ignore = true)
    @Mapping(target = "entrance", ignore = true)
    @Mapping(target = "building", ignore = true)
    Room toEntity(RoomRequest request);
}
