package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Entrance;
import com.example.real_estate_app_account.model.Floor;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.EntranceRepository;
import com.example.real_estate_app_account.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FloorService {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;
    private final EntranceRepository entranceRepository;

    /**
     * Создание нового этажа.
     *
     * @param request DTO с данными для создания этажа.
     * @return Сохранённый этаж.
     */
    public FloorResponse createFloor(FloorRequest request) {
        log.info("Попытка создать этаж с номером: {}", request.getFloorNumber());

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new IllegalArgumentException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new IllegalArgumentException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        if (floorRepository.existsByBuildingAndEntranceAndFloorNumber(building, entrance, request.getFloorNumber())) {
            log.error("Этаж с номером {} в здании с ID {} и подъезде с ID {} уже существует",
                request.getFloorNumber(), request.getBuildingId(), request.getEntranceId());
            throw new IllegalArgumentException("Этаж с таким номером уже существует в этом здании и подъезде");
        }

        Floor floor = new Floor();
        floor.setFloorNumber(request.getFloorNumber());
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor savedFloor = floorRepository.save(floor);
        log.info("Этаж с ID {} успешно создан", savedFloor.getId());
        return mapToResponse(savedFloor);
    }

    /**
     * Получение списка всех этажей.
     *
     * @return Список всех этажей.
     */
    public List<FloorResponse> getAllFloors() {
        log.info("Запрос всех этажей");

        List<FloorResponse> floors = floorRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        log.info("Найдено {} этажей", floors.size());
        return floors;
    }

    /**
     * Получение этажа по его ID.
     *
     * @param id ID этажа.
     * @return Найденный этаж.
     * @throws IllegalArgumentException если этаж не найден.
     */
    public FloorResponse getFloorById(Long id) {
        log.info("Запрос этажа с ID: {}", id);

        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Этаж с ID {} не найден", id);
                return new IllegalArgumentException("Этаж с ID " + id + " не найден");
            });

        log.info("Этаж с ID {} найден", id);
        return mapToResponse(floor);
    }

    /**
     * Обновление этажа по его ID.
     *
     * @param id ID этажа.
     * @param request DTO с новыми данными этажа.
     * @return Обновлённый этаж.
     * @throws IllegalArgumentException если этаж не найден.
     */
    public FloorResponse updateFloor(Long id, FloorRequest request) {
        log.info("Попытка обновить этаж с ID: {}", id);

        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Этаж с ID {} не найден", id);
                return new IllegalArgumentException("Этаж с ID " + id + " не найден");
            });

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new IllegalArgumentException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new IllegalArgumentException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        if (floorRepository.existsByBuildingAndEntranceAndFloorNumber(building, entrance, request.getFloorNumber())) {
            log.error("Этаж с номером {} в здании с ID {} и подъезде с ID {} уже существует",
                request.getFloorNumber(), request.getBuildingId(), request.getEntranceId());
            throw new IllegalArgumentException("Этаж с таким номером уже существует в этом здании и подъезде");
        }

        floor.setFloorNumber(request.getFloorNumber());
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor updatedFloor = floorRepository.save(floor);
        log.info("Этаж с ID {} успешно обновлён", updatedFloor.getId());
        return mapToResponse(updatedFloor);
    }

    /**
     * Удаление этажа по его ID.
     *
     * @param id ID этажа.
     * @throws IllegalArgumentException если этаж не найден.
     */
    public void deleteFloor(Long id) {
        log.info("Попытка удалить этаж с ID: {}", id);

        Floor floor = floorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Этаж с ID {} не найден", id);
                return new IllegalArgumentException("Этаж с ID " + id + " не найден");
            });

        floorRepository.delete(floor);
        log.info("Этаж с ID {} успешно удалён", id);
    }

    /**
     * Преобразование сущности этажа в ответ.
     *
     * @param floor Сущность этажа.
     * @return DTO для ответа с данными этажа.
     */
    private FloorResponse mapToResponse(Floor floor) {
        FloorResponse response = new FloorResponse();
        response.setId(floor.getId());
        response.setFloorNumber(floor.getFloorNumber());
        response.setBuildingId(floor.getBuilding().getId());
        response.setBuildingName(floor.getBuilding().getName());
        response.setEntranceId(floor.getEntrance().getId());
        response.setEntranceName(floor.getEntrance().getEntranceName());
        return response;
    }
}
