package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.CorridorRequest;
import com.example.real_estate_app_account.dto.CorridorResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.FloorNotFoundException;
import com.example.real_estate_app_account.mapper.CorridorMapper;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Corridor;
import com.example.real_estate_app_account.model.Floor;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.CorridorRepository;
import com.example.real_estate_app_account.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorridorService {

    private final CorridorRepository corridorRepository;
    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;
    private final CorridorMapper corridorMapper;


    /**
     * Создание нового коридора.
     *
     * @param request DTO с данными для создания коридора.
     * @return Сохранённый коридор в виде DTO.
     */
    public CorridorResponse createCorridor(CorridorRequest request) {
        log.info("Попытка создания коридора с названием: {}", request.getName());

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> {
                log.error("Этаж с ID {} не найден", request.getFloorId());
                return new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден");
            });

        Corridor corridor = corridorMapper.toEntity(request);
        corridor.setFloor(floor);

        Corridor savedCorridor = corridorRepository.save(corridor);
        log.info("Коридор с ID {} успешно создан", savedCorridor.getId());
        return corridorMapper.toResponse(savedCorridor);
    }

    /**
     * Получение списка всех коридоров.
     *
     * @return Список коридоров в виде DTO.
     */
    public List<CorridorResponse> getAllCorridors() {
        log.info("Запрос списка всех коридоров");

        List<CorridorResponse> responses = corridorRepository.findAll()
            .stream()
            .map(corridorMapper::toResponse)
            .collect(Collectors.toList());

        log.info("Найдено {} коридоров", responses.size());
        return responses;
    }

    /**
     * Получение коридора по его ID.
     *
     * @param id ID коридора.
     * @return Найденный коридор в виде DTO.
     * @throws RuntimeException если коридор не найден.
     */
    public CorridorResponse getCorridorById(Long id) {
        log.info("Запрос коридора с ID: {}", id);

        Corridor corridor = corridorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Коридор с ID {} не найден", id);
                return new RuntimeException("Коридор с ID " + id + " не найден");
            });

        log.info("Коридор с ID {} найден", id);
        return corridorMapper.toResponse(corridor);
    }

    /**
     * Обновление коридора по его ID.
     *
     * @param id      ID коридора.
     * @param request DTO с новыми данными коридора.
     * @return Обновлённый коридор в виде DTO.
     * @throws RuntimeException если коридор не найден.
     */
    public CorridorResponse updateCorridor(Long id, CorridorRequest request) {
        log.info("Попытка обновления коридора с ID: {}", id);

        Corridor corridor = corridorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Коридор с ID {} не найден", id);
                return new RuntimeException("Коридор с ID " + id + " не найден");
            });

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> {
                log.error("Этаж с ID {} не найден", request.getFloorId());
                return new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден");
            });

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        corridor.setName(request.getName());
        corridor.setFloor(floor);
        corridor.setBuilding(building);

        Corridor updatedCorridor = corridorRepository.save(corridor);
        log.info("Коридор с ID {} успешно обновлён", updatedCorridor.getId());
        return corridorMapper.toResponse(updatedCorridor);
    }

    /**
     * Удаление коридора по его ID.
     *
     * @param id ID коридора.
     */
    public void deleteCorridor(Long id) {
        log.info("Попытка удаления коридора с ID: {}", id);

        corridorRepository.deleteById(id);
        log.info("Коридор с ID {} успешно удалён", id);
    }
}
