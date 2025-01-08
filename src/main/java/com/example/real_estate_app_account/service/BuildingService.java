package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildingService {

    private final BuildingRepository buildingRepository;

    /**
     * Создание нового здания.
     *
     * @param building DTO с данными нового здания.
     * @return Сохранённое здание.
     */
    public Building createBuilding(Building building) {
        log.info("Сохранение нового здания: {}", building);
        return buildingRepository.save(building);
    }

    /**
     * Получение списка всех зданий.
     *
     * @return Список всех зданий.
     */
    public List<Building> getAllBuildings() {
        log.info("Получение списка всех зданий.");
        return buildingRepository.findAll();
    }

    /**
     * Получение здания по его ID.
     *
     * @param id ID здания.
     * @return Найденное здание.
     * @throws EntityNotFoundException если здание не найдено.
     */
    public Building findBuildingById(Long id) {
        log.info("Поиск здания с ID: {}", id);
        return buildingRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Здание с ID: {} не найдено.", id);
                return new EntityNotFoundException("Здание с ID " + id + " не найдено.");
            });
    }

    /**
     * Удаление здания по его ID.
     *
     * @param id ID здания.
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
