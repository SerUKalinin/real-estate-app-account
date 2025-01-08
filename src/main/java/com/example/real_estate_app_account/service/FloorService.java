package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Entrance;
import com.example.real_estate_app_account.model.Floor;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.EntranceRepository;
import com.example.real_estate_app_account.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FloorService {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;
    private final EntranceRepository entranceRepository;

    public FloorResponse createFloor(FloorRequest request) {
        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new IllegalArgumentException("Здание с ID " + request.getBuildingId() + " не найдено"));

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> new IllegalArgumentException("Подъезд с ID " + request.getEntranceId() + " не найден"));

        Floor floor = new Floor();
        floor.setFloorNumber(request.getFloorNumber());
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor savedFloor = floorRepository.save(floor);
        return mapToResponse(savedFloor);
    }

    public List<FloorResponse> getAllFloors() {
        return floorRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public FloorResponse getFloorById(Long id) {
        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Этаж с ID " + id + " не найден"));
        return mapToResponse(floor);
    }

    public FloorResponse updateFloor(Long id, FloorRequest request) {
        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Этаж с ID " + id + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new IllegalArgumentException("Здание с ID " + request.getBuildingId() + " не найдено"));

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> new IllegalArgumentException("Подъезд с ID " + request.getEntranceId() + " не найден"));

        floor.setFloorNumber(request.getFloorNumber());
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor updatedFloor = floorRepository.save(floor);
        return mapToResponse(updatedFloor);
    }

    public void deleteFloor(Long id) {
        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Этаж с ID " + id + " не найден"));
        floorRepository.delete(floor);
    }

    private FloorResponse mapToResponse(Floor floor) {
        FloorResponse response = new FloorResponse();
        response.setId(floor.getId());
        response.setFloorNumber(floor.getFloorNumber());
        response.setBuildingId(floor.getBuilding().getId());
        response.setBuildingName(floor.getBuilding().getName());
        response.setEntranceId(floor.getEntrance().getId());
        response.setEntranceName(floor.getEntrance().getEntranceName());
        return response;
    }
}
