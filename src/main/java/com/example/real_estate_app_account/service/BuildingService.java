package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Optional<Building> getBuildingById(Long id) {
        return buildingRepository.findById(id);
    }

    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
