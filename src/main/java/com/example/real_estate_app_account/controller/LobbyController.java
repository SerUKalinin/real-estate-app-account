package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.LobbyRequest;
import com.example.real_estate_app_account.dto.LobbyResponse;
import com.example.real_estate_app_account.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lobby")
@RequiredArgsConstructor
public class LobbyController {

    private final LobbyService lobbyService;

    /**
     * Создание нового лифтового холла.
     *
     * @param request DTO с данными для создания лифтового холла.
     * @return ResponseEntity с данными созданного лифтового холла.
     */
    @PostMapping
    public ResponseEntity<LobbyResponse> createLobby(@RequestBody LobbyRequest request) {
        LobbyResponse response = lobbyService.createLobby(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Получение списка всех лифтовых холлов.
     *
     * @return ResponseEntity с данными всех лифтовых холлов.
     */
    @GetMapping
    public ResponseEntity<List<LobbyResponse>> getAllLobbies() {
        List<LobbyResponse> responses = lobbyService.getAllLobbies();
        return ResponseEntity.ok(responses);
    }

    /**
     * Получение лифтового холла по его ID.
     *
     * @param id ID лифтового холла.
     * @return ResponseEntity с данными лифтового холла.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LobbyResponse> getLobbyById(@PathVariable Long id) {
        LobbyResponse response = lobbyService.getLobbyById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Обновление лифтового холла.
     *
     * @param id ID лифтового холла.
     * @param request DTO с новыми данными для обновления лифтового холла.
     * @return ResponseEntity с обновлённым лифтовым холлом.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LobbyResponse> updateLobby(
        @PathVariable Long id,
        @RequestBody LobbyRequest request) {
        LobbyResponse response = lobbyService.updateLobby(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаление лифтового холла.
     *
     * @param id ID лифтового холла.
     * @return ResponseEntity с пустым телом.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLobby(@PathVariable Long id) {
        lobbyService.deleteLobby(id);
        return ResponseEntity.noContent().build();
    }
}
