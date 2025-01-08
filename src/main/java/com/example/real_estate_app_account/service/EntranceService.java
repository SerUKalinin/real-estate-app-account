package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.EntranceRequest;
import com.example.real_estate_app_account.dto.EntranceResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.EntranceAlreadyExistsException;
import com.example.real_estate_app_account.exception.EntranceNotFoundException;
import com.example.real_estate_app_account.mapper.EntranceMapper;
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
    private final EntranceMapper entranceMapper;


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
        return entranceMapper.toEntranceResponse(savedEntrance);
    }

    /**
     * Получение списка всех подъездов.
     *
     * @return Список всех подъездов.
     */
    public List<EntranceResponse> getAllEntrances() {
        log.info("Запрос списка всех подъездов");
        return entranceRepository.findAll()
            .stream()
            .map(entranceMapper::toEntranceResponse)
            .collect(Collectors.toList());
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
        return entranceMapper.toEntranceResponse(entrance);
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
        return entranceMapper.toEntranceResponse(updatedEntrance);
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
}
