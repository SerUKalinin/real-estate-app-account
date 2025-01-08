package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    public ResponseEntity<FloorResponse> createFloor(@RequestBody FloorRequest request) {
        FloorResponse response = floorService.createFloor(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FloorResponse>> getAllFloors() {
        List<FloorResponse> responses = floorService.getAllFloors();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorResponse> getFloorById(@PathVariable Long id) {
        FloorResponse response = floorService.getFloorById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorResponse> updateFloor(
        @PathVariable Long id,
        @RequestBody FloorRequest request) {
        FloorResponse response = floorService.updateFloor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
        return ResponseEntity.noContent().build();
    }
}
