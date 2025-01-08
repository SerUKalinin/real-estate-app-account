package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.FloorRequest;
import com.example.real_estate_app_account.dto.FloorResponse;
import com.example.real_estate_app_account.mapper.FloorMapper;
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
    private final FloorMapper floorMapper;


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
            log.error("Этаж с номером {} уже существует", request.getFloorNumber());
            throw new IllegalArgumentException("Этаж с таким номером уже существует");
        }

        Floor floor = floorMapper.toEntity(request);
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor savedFloor = floorRepository.save(floor);
        log.info("Этаж с ID {} успешно создан", savedFloor.getId());
        return floorMapper.toResponse(savedFloor);
    }

    /**
     * Получение списка всех этажей.
     *
     * @return Список всех этажей.
     */
    public List<FloorResponse> getAllFloors() {
        log.info("Запрос всех этажей");
        return floorRepository.findAll()
            .stream()
            .map(floorMapper::toResponse)
            .collect(Collectors.toList());
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
        return floorMapper.toResponse(floor);
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
            log.error("Этаж с номером {} уже существует", request.getFloorNumber());
            throw new IllegalArgumentException("Этаж с таким номером уже существует");
        }

        floor.setFloorNumber(request.getFloorNumber());
        floor.setBuilding(building);
        floor.setEntrance(entrance);

        Floor updatedFloor = floorRepository.save(floor);
        log.info("Этаж с ID {} успешно обновлён", updatedFloor.getId());
        return floorMapper.toResponse(updatedFloor);
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
}
