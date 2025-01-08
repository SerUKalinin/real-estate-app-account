package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.ApartmentRequest;
import com.example.real_estate_app_account.dto.ApartmentResponse;
import com.example.real_estate_app_account.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentResponse> createApartment(@RequestBody ApartmentRequest request) {
        return ResponseEntity.ok(apartmentService.createApartment(request));
    }

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAllApartments() {
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getApartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> updateApartment(@PathVariable Long id, @RequestBody ApartmentRequest request) {
        return ResponseEntity.ok(apartmentService.updateApartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }
}
