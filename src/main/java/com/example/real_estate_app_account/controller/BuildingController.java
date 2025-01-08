package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.BuildingResponse;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
@Slf4j
public class BuildingController {

    private final BuildingService buildingService;

    /**
     * Создание нового здания.
     *
     * @param building Данные здания.
     * @return Ответ с созданным зданием.
     */
    @PostMapping
    public ResponseEntity<BuildingResponse> createBuilding(@RequestBody Building building) {
        log.info("Попытка создания нового здания.");
        BuildingResponse buildingResponse = buildingService.createBuilding(building);
        return ResponseEntity.status(201).body(buildingResponse);  // Возвращаем статус 201 с созданным зданием
    }

    /**
     * Получение списка всех зданий.
     *
     * @return Список зданий.
     */
    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getAllBuildings() {
        log.info("Запрос списка всех зданий.");
        List<BuildingResponse> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);  // Возвращаем список зданий с кодом 200
    }

    /**
     * Получение здания по его ID.
     *
     * @param id ID здания.
     * @return Ответ с найденным зданием.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getBuildingById(@PathVariable Long id) {
        log.info("Запрос здания с ID: {}", id);
        BuildingResponse buildingResponse = buildingService.findBuildingById(id);
        return ResponseEntity.ok(buildingResponse);  // Возвращаем здание с кодом 200
    }

    /**
     * Удаление здания по ID.
     *
     * @param id ID здания.
     * @return Ответ без содержимого (204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.info("Попытка удаления здания с ID: {}", id);
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();  // Возвращаем статус 204 без содержимого
    }
}
