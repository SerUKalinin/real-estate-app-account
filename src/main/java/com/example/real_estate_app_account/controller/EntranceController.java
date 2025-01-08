package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.EntranceRequest;
import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.service.EntranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления подъездами в системе недвижимости.
 * Предоставляет API для создания, получения, обновления и удаления подъездов.
 */
@RestController
@RequestMapping("/api/entrances")
@RequiredArgsConstructor
public class EntranceController {

    private final EntranceService entranceService;

    /**
     * Создание нового подъезда.
     *
     * @param request DTO с данными для создания нового подъезда.
     * @return ResponseEntity с объектом созданного подъезда и статусом 200 OK.
     */
    @PostMapping
    public ResponseEntity<EntranceResponse> createEntrance(@RequestBody EntranceRequest request) {
        EntranceResponse response = entranceService.createEntrance(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Получение списка всех подъездов.
     *
     * @return ResponseEntity со списком всех подъездов и статусом 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<EntranceResponse>> getAllEntrances() {
        List<EntranceResponse> responses = entranceService.getAllEntrances();
        return ResponseEntity.ok(responses);
    }

    /**
     * Получение подъезда по его ID.
     *
     * @param id ID подъезда.
     * @return ResponseEntity с объектом подъезда или статусом 404 NOT FOUND, если подъезд не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntranceResponse> getEntranceById(@PathVariable Long id) {
        EntranceResponse response = entranceService.getEntranceById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Обновление данных подъезда по его ID.
     *
     * @param id ID подъезда, который нужно обновить.
     * @param request DTO с новыми данными для обновления подъезда.
     * @return ResponseEntity с объектом обновленного подъезда и статусом 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntranceResponse> updateEntrance(
        @PathVariable Long id,
        @RequestBody EntranceRequest request) {
        EntranceResponse response = entranceService.updateEntrance(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаление подъезда по его ID.
     *
     * @param id ID подъезда, который нужно удалить.
     * @return ResponseEntity с пустым телом и статусом 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrance(@PathVariable Long id) {
        entranceService.deleteEntrance(id);
        return ResponseEntity.noContent().build();
    }
}
