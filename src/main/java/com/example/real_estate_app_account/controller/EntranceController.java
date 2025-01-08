package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.EntranceRequest;
import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.service.EntranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrances")
@RequiredArgsConstructor
public class EntranceController {

    private final EntranceService entranceService;

    @PostMapping
    public ResponseEntity<EntranceResponse> createEntrance(@RequestBody EntranceRequest request) {
        EntranceResponse response = entranceService.createEntrance(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EntranceResponse>> getAllEntrances() {
        List<EntranceResponse> responses = entranceService.getAllEntrances();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntranceResponse> getEntranceById(@PathVariable Long id) {
        EntranceResponse response = entranceService.getEntranceById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntranceResponse> updateEntrance(
        @PathVariable Long id,
        @RequestBody EntranceRequest request) {
        EntranceResponse response = entranceService.updateEntrance(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrance(@PathVariable Long id) {
        entranceService.deleteEntrance(id);
        return ResponseEntity.noContent().build();
    }
}
