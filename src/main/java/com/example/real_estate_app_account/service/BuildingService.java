package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.BuildingResponse;
import com.example.real_estate_app_account.mapper.BuildingMapper;
import com.example.real_estate_app_account.model.Address;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.repository.AddressRepository;
import com.example.real_estate_app_account.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final AddressRepository addressRepository;

    /**
     * Создание нового здания.
     *
     * @param building Сущность здания.
     * @return ResponseEntity с созданным зданием.
     */
    public BuildingResponse createBuilding(Building building) {
        log.info("Попытка создания здания с названием: {}", building.getName());

        if (buildingRepository.existsByName(building.getName())) {
            log.error("Здание с названием {} уже существует", building.getName());
            throw new IllegalArgumentException("Здание с таким названием уже существует");
        }

        if (addressRepository.existsByStreetAndNumberHouseAndCity(building.getAddress().getStreet(),
            building.getAddress().getNumberHouse(), building.getAddress().getCity())) {
            log.info("Адрес {} уже существует. Используем существующий.", building.getAddress());
            Address existingAddress = addressRepository.findByStreetAndNumberHouseAndCity(
                building.getAddress().getStreet(), building.getAddress().getNumberHouse(), building.getAddress().getCity());
            building.setAddress(existingAddress);
        }

        log.info("Сохранение нового здания: {}", building);
        Building savedBuilding = buildingRepository.save(building);
        return buildingMapper.toBuildingResponse(savedBuilding);
    }

    /**
     * Получение списка всех зданий.
     *
     * @return ResponseEntity со списком всех зданий.
     */
    public List<BuildingResponse> getAllBuildings() {
        log.info("Получение списка всех зданий.");
        return buildingRepository.findAll()
            .stream()
            .map(buildingMapper::toBuildingResponse)
            .collect(Collectors.toList());
    }

    /**
     * Получение здания по ID.
     *
     * @param id ID здания.
     * @return ResponseEntity с найденным зданием.
     */
    public BuildingResponse findBuildingById(Long id) {
        log.info("Поиск здания с ID: {}", id);
        Building building = buildingRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Здание с ID: {} не найдено.", id);
                return new EntityNotFoundException("Здание с ID " + id + " не найдено.");
            });
        return buildingMapper.toBuildingResponse(building);
    }

    /**
     * Удаление здания по ID.
     *
     * @param id ID здания.
     * @return ResponseEntity с результатом удаления.
     */
    public void deleteBuilding(Long id) {
        log.info("Удаление здания с ID: {}", id);
        if (!buildingRepository.existsById(id)) {
            log.warn("Здание с ID: {} не найдено для удаления.", id);
            throw new EntityNotFoundException("Здание с ID " + id + " не найдено.");
        }
        buildingRepository.deleteById(id);
        log.info("Здание с ID: {} успешно удалено.", id);
    }
}
