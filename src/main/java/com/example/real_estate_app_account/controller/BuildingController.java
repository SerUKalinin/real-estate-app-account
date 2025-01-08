package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления объектами недвижимости (зданиями).
 * Предоставляет API для создания, получения и удаления зданий.
 */
@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
@Slf4j
public class BuildingController {

    private final BuildingService buildingService;

    /**
     * Создание нового здания.
     *
     * @param building DTO с данными нового здания.
     * @return ResponseEntity с созданным объектом здания и статусом 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Building> createBuilding(@RequestBody Building building) {
        log.info("Попытка создания нового здания.");
        return ResponseEntity.status(201).body(buildingService.createBuilding(building));
    }

    /**
     * Получение списка всех зданий.
     *
     * @return ResponseEntity со списком всех зданий и статусом 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Building>> getAllBuildings() {
        log.info("Запрос списка всех зданий.");
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    /**
     * Получение здания по его ID.
     *
     * @param id ID здания.
     * @return ResponseEntity с объектом здания или статусом 404 NOT FOUND, если здание не найдено.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
        log.info("Запрос здания с ID: {}", id);
        return ResponseEntity.ok(buildingService.findBuildingById(id));
    }

    /**
     * Удаление здания по его ID.
     *
     * @param id ID здания.
     * @return ResponseEntity со статусом 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.info("Попытка удаления здания с ID: {}", id);
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }
}
