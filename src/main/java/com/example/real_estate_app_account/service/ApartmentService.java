package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.ApartmentRequest;
import com.example.real_estate_app_account.dto.ApartmentResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.FloorNotFoundException;
import com.example.real_estate_app_account.model.Apartment;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Floor;
import com.example.real_estate_app_account.repository.ApartmentRepository;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    public ApartmentResponse createApartment(ApartmentRequest request) {
        log.info("Попытка создания квартиры с номером: {}", request.getNumber());

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено"));

        Apartment apartment = new Apartment();
        apartment.setName(request.getName());
        apartment.setNumber(request.getNumber());
        apartment.setFloor(floor);
        apartment.setBuilding(building);
        apartment.setArea(request.getArea());

        Apartment savedApartment = apartmentRepository.save(apartment);
        log.info("Квартира с ID {} успешно создана", savedApartment.getId());
        return mapToResponse(savedApartment);
    }

    public List<ApartmentResponse> getAllApartments() {
        log.info("Запрос списка всех квартир");
        return apartmentRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public ApartmentResponse getApartmentById(Long id) {
        log.info("Запрос квартиры с ID: {}", id);
        Apartment apartment = apartmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Квартира с ID " + id + " не найдена"));
        return mapToResponse(apartment);
    }

    public ApartmentResponse updateApartment(Long id, ApartmentRequest request) {
        log.info("Попытка обновления квартиры с ID: {}", id);
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

        Apartment updatedApartment = apartmentRepository.save(apartment);
        log.info("Квартира с ID {} успешно обновлена", updatedApartment.getId());
        return mapToResponse(updatedApartment);
    }

    public void deleteApartment(Long id) {
        log.info("Попытка удаления квартиры с ID: {}", id);
        apartmentRepository.deleteById(id);
        log.info("Квартира с ID {} успешно удалена", id);
    }

    private ApartmentResponse mapToResponse(Apartment apartment) {
        ApartmentResponse response = new ApartmentResponse();
        response.setId(apartment.getId());
        response.setName(apartment.getName());
        response.setNumber(apartment.getNumber());
        response.setFloorId(apartment.getFloor().getId());
        response.setBuildingId(apartment.getBuilding().getId());
        response.setArea(apartment.getArea());
        return response;
    }
}
