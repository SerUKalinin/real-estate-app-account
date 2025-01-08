package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.ApartmentRequest;
import com.example.real_estate_app_account.dto.ApartmentResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.FloorNotFoundException;
import com.example.real_estate_app_account.mapper.ApartmentMapper;
import com.example.real_estate_app_account.model.Apartment;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Floor;
import com.example.real_estate_app_account.repository.ApartmentRepository;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;
    private final ApartmentMapper apartmentMapper;

    public ApartmentResponse createApartment(ApartmentRequest request) {
        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено"));

        Apartment apartment = apartmentMapper.toEntity(request);
        apartment.setFloor(floor);
        apartment.setBuilding(building);

        return apartmentMapper.toResponse(apartmentRepository.save(apartment));
    }

    public List<ApartmentResponse> getAllApartments() {
        return apartmentRepository.findAll()
            .stream()
            .map(apartmentMapper::toResponse)
            .collect(Collectors.toList());
    }

    public ApartmentResponse getApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Квартира с ID " + id + " не найдена"));
        return apartmentMapper.toResponse(apartment);
    }

    public ApartmentResponse updateApartment(Long id, ApartmentRequest request) {
        Apartment apartment = apartmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Квартира с ID " + id + " не найдена"));

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено"));

        apartment.setName(request.getName());
        apartment.setNumber(request.getNumber());
        apartment.setFloor(floor);
        apartment.setBuilding(building);
        apartment.setArea(request.getArea());

        return apartmentMapper.toResponse(apartmentRepository.save(apartment));
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}
