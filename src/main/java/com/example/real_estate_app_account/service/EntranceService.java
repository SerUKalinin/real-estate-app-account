package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.EntranceRequest;
import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.EntranceNotFoundException;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Entrance;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.EntranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntranceService {

    private final EntranceRepository entranceRepository;
    private final BuildingRepository buildingRepository;

    public EntranceResponse createEntrance(EntranceRequest request) {
        log.info("Попытка создания подъезда с названием: {}", request.getEntranceName());

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        Entrance entrance = new Entrance();
        entrance.setEntranceName(request.getEntranceName());
        entrance.setBuilding(building);

        Entrance savedEntrance = entranceRepository.save(entrance);
        log.info("Подъезд с ID {} успешно создан", savedEntrance.getId());
        return mapToResponse(savedEntrance);
    }

    public List<EntranceResponse> getAllEntrances() {
        log.info("Запрос списка всех подъездов");

        List<EntranceResponse> responses = entranceRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        log.info("Найдено {} подъездов", responses.size());
        return responses;
    }

    public EntranceResponse getEntranceById(Long id) {
        log.info("Запрос подъезда с ID: {}", id);

        Entrance entrance = entranceRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", id);
                return new EntranceNotFoundException("Подъезд с ID " + id + " не найден");
            });

        log.info("Подъезд с ID {} найден", id);
        return mapToResponse(entrance);
    }

    public EntranceResponse updateEntrance(Long id, EntranceRequest request) {
        log.info("Попытка обновления подъезда с ID: {}", id);

        Entrance entrance = entranceRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", id);
                return new EntranceNotFoundException("Подъезд с ID " + id + " не найден");
            });

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        entrance.setEntranceName(request.getEntranceName());
        entrance.setBuilding(building);

        Entrance updatedEntrance = entranceRepository.save(entrance);
        log.info("Подъезд с ID {} успешно обновлен", updatedEntrance.getId());
        return mapToResponse(updatedEntrance);
    }

    public void deleteEntrance(Long id) {
        log.info("Попытка удаления подъезда с ID: {}", id);

        Entrance entrance = entranceRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", id);
                return new EntranceNotFoundException("Подъезд с ID " + id + " не найден");
            });

        entranceRepository.delete(entrance);
        log.info("Подъезд с ID {} успешно удален", id);
    }

    private EntranceResponse mapToResponse(Entrance entrance) {
        EntranceResponse response = new EntranceResponse();
        response.setId(entrance.getId());
        response.setEntranceName(entrance.getEntranceName());
        response.setBuildingId(entrance.getBuilding().getId());
        response.setBuildingName(entrance.getBuilding().getName());
        return response;
    }
}
