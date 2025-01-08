package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления этажами в системе недвижимости.
 * Предоставляет API для создания, получения, обновления и удаления этажей.
 */
@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;

    /**
     * Создание нового этажа.
     *
     * @param request DTO с данными для создания нового этажа.
     * @return ResponseEntity с объектом созданного этажа и статусом 200 OK.
     */
    @PostMapping
    public ResponseEntity<FloorResponse> createFloor(@RequestBody FloorRequest request) {
        FloorResponse response = floorService.createFloor(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Получение списка всех этажей.
     *
     * @return ResponseEntity со списком всех этажей и статусом 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<FloorResponse>> getAllFloors() {
        List<FloorResponse> responses = floorService.getAllFloors();
        return ResponseEntity.ok(responses);
    }

    /**
     * Получение этажа по его ID.
     *
     * @param id ID этажа.
     * @return ResponseEntity с объектом этажа или статусом 404 NOT FOUND, если этаж не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FloorResponse> getFloorById(@PathVariable Long id) {
        FloorResponse response = floorService.getFloorById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Обновление данных этажа по его ID.
     *
     * @param id ID этажа, который нужно обновить.
     * @param request DTO с новыми данными для обновления этажа.
     * @return ResponseEntity с объектом обновленного этажа и статусом 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FloorResponse> updateFloor(
        @PathVariable Long id,
        @RequestBody FloorRequest request) {
        FloorResponse response = floorService.updateFloor(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаление этажа по его ID.
     *
     * @param id ID этажа, который нужно удалить.
     * @return ResponseEntity с пустым телом и статусом 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
        return ResponseEntity.noContent().build();
    }
}
