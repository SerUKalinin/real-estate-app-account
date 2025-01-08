package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.CorridorRequest;
import com.example.real_estate_app_account.dto.CorridorResponse;
import com.example.real_estate_app_account.service.CorridorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corridors")
@RequiredArgsConstructor
public class CorridorController {

    private final CorridorService corridorService;

    /**
     * Создание нового коридора.
     *
     * @param request DTO с данными для создания коридора.
     * @return Созданный коридор в виде DTO.
     */
    @PostMapping
    public ResponseEntity<CorridorResponse> createCorridor(@RequestBody CorridorRequest request) {
        return ResponseEntity.ok(corridorService.createCorridor(request));
    }

    /**
     * Получение списка всех коридоров.
     *
     * @return Список всех коридоров в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<CorridorResponse>> getAllCorridors() {
        return ResponseEntity.ok(corridorService.getAllCorridors());
    }

    /**
     * Получение коридора по его ID.
     *
     * @param id ID коридора.
     * @return Найденный коридор в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CorridorResponse> getCorridorById(@PathVariable Long id) {
        return ResponseEntity.ok(corridorService.getCorridorById(id));
    }

    /**
     * Обновление коридора по его ID.
     *
     * @param id      ID коридора.
     * @param request DTO с новыми данными коридора.
     * @return Обновлённый коридор в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CorridorResponse> updateCorridor(@PathVariable Long id, @RequestBody CorridorRequest request) {
        return ResponseEntity.ok(corridorService.updateCorridor(id, request));
    }

    /**
     * Удаление коридора по его ID.
     *
     * @param id ID коридора.
     * @return Пустой ответ с HTTP статусом 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorridor(@PathVariable Long id) {
        corridorService.deleteCorridor(id);
        return ResponseEntity.noContent().build();
    }
}
