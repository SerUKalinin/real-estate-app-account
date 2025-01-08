package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.EntranceRequest;
import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.EntranceAlreadyExistsException;
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

    /**
     * Создание нового подъезда.
     *
     * @param request DTO с данными для создания подъезда.
     * @return Сохранённый подъезд.
     */
    public EntranceResponse createEntrance(EntranceRequest request) {
        log.info("Попытка создания подъезда с названием: {}", request.getEntranceName());

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        if (entranceRepository.existsByBuildingAndEntranceName(building, request.getEntranceName())) {
            log.error("Подъезд с названием {} в здании с ID {} уже существует", request.getEntranceName(), request.getBuildingId());
            throw new EntranceAlreadyExistsException("Подъезд с таким названием уже существует в этом здании");
        }

        Entrance entrance = new Entrance();
        entrance.setEntranceName(request.getEntranceName());
        entrance.setBuilding(building);

        Entrance savedEntrance = entranceRepository.save(entrance);
        log.info("Подъезд с ID {} успешно создан", savedEntrance.getId());
        return mapToResponse(savedEntrance);
    }

    /**
     * Получение списка всех подъездов.
     *
     * @return Список всех подъездов.
     */
    public List<EntranceResponse> getAllEntrances() {
        log.info("Запрос списка всех подъездов");

        List<EntranceResponse> responses = entranceRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        log.info("Найдено {} подъездов", responses.size());
        return responses;
    }

    /**
     * Получение подъезда по его ID.
     *
     * @param id ID подъезда.
     * @return Найденный подъезд.
     * @throws EntranceNotFoundException если подъезд не найден.
     */
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

    /**
     * Обновление подъезда по его ID.
     *
     * @param id ID подъезда.
     * @param request DTO с новыми данными подъезда.
     * @return Обновлённый подъезд.
     * @throws EntranceNotFoundException если подъезд не найден.
     */
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

        if (entranceRepository.existsByBuildingAndEntranceName(building, request.getEntranceName())) {
            log.error("Подъезд с названием {} в здании с ID {} уже существует", request.getEntranceName(), request.getBuildingId());
            throw new EntranceAlreadyExistsException("Подъезд с таким названием уже существует в этом здании");
        }

        entrance.setEntranceName(request.getEntranceName());
        entrance.setBuilding(building);

        Entrance updatedEntrance = entranceRepository.save(entrance);
        log.info("Подъезд с ID {} успешно обновлён", updatedEntrance.getId());
        return mapToResponse(updatedEntrance);
    }

    /**
     * Удаление подъезда по его ID.
     *
     * @param id ID подъезда.
     * @throws EntranceNotFoundException если подъезд не найден.
     */
    public void deleteEntrance(Long id) {
        log.info("Попытка удаления подъезда с ID: {}", id);

        Entrance entrance = entranceRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", id);
                return new EntranceNotFoundException("Подъезд с ID " + id + " не найден");
            });

        entranceRepository.delete(entrance);
        log.info("Подъезд с ID {} успешно удалён", id);
    }

    /**
     * Преобразование сущности подъезда в ответ.
     *
     * @param entrance Сущность подъезда.
     * @return DTO для ответа с данными подъезда.
     */
    private EntranceResponse mapToResponse(Entrance entrance) {
        EntranceResponse response = new EntranceResponse();
        response.setId(entrance.getId());
        response.setEntranceName(entrance.getEntranceName());
        response.setBuildingId(entrance.getBuilding().getId());
        response.setBuildingName(entrance.getBuilding().getName());
        return response;
    }
}
