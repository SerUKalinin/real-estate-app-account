package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.ElevatorRequest;
import com.example.real_estate_app_account.dto.ElevatorResponse;
import com.example.real_estate_app_account.service.ElevatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elevators")
@RequiredArgsConstructor
public class ElevatorController {

    private final ElevatorService elevatorService;

    /**
     * Создание нового лифта.
     *
     * @param request DTO с данными для создания лифта.
     * @return Ответ с данными созданного лифта.
     */
    @PostMapping
    public ResponseEntity<ElevatorResponse> createElevator(@RequestBody ElevatorRequest request) {
        ElevatorResponse response = elevatorService.createElevator(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Получение списка всех лифтов.
     *
     * @return Список всех лифтов.
     */
    @GetMapping
    public ResponseEntity<List<ElevatorResponse>> getAllElevators() {
        List<ElevatorResponse> responses = elevatorService.getAllElevators();
        return ResponseEntity.ok(responses);
    }

    /**
     * Получение лифта по его ID.
     *
     * @param id ID лифта.
     * @return Ответ с данными лифта.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ElevatorResponse> getElevatorById(@PathVariable Long id) {
        ElevatorResponse response = elevatorService.getElevatorById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Обновление лифта по его ID.
     *
     * @param id ID лифта.
     * @param request DTO с новыми данными лифта.
     * @return Ответ с обновленными данными лифта.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ElevatorResponse> updateElevator(
        @PathVariable Long id,
        @RequestBody ElevatorRequest request) {
        ElevatorResponse response = elevatorService.updateElevator(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаление лифта по его ID.
     *
     * @param id ID лифта.
     * @return Ответ без содержимого (204 No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElevator(@PathVariable Long id) {
        elevatorService.deleteElevator(id);
        return ResponseEntity.noContent().build();
    }
}
