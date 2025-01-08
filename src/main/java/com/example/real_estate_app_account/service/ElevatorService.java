package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.ElevatorRequest;
import com.example.real_estate_app_account.dto.ElevatorResponse;
import com.example.real_estate_app_account.exception.EntranceNotFoundException;
import com.example.real_estate_app_account.exception.ElevatorNotFoundException;
import com.example.real_estate_app_account.mapper.ElevatorMapper;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Entrance;
import com.example.real_estate_app_account.model.Elevator;
import com.example.real_estate_app_account.repository.EntranceRepository;
import com.example.real_estate_app_account.repository.ElevatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElevatorService {

    private final ElevatorRepository elevatorRepository;
    private final EntranceRepository entranceRepository;
    private final ElevatorMapper elevatorMapper;


    /**
     * Создание нового лифта.
     *
     * @param request DTO с данными для создания лифта.
     * @return Сохранённый лифт.
     */
    public ElevatorResponse createElevator(ElevatorRequest request) {
        log.info("Попытка создания лифта с названием: {}", request.getName());

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        Elevator elevator = elevatorMapper.toEntity(request);
        elevator.setEntrance(entrance);

        Elevator savedElevator = elevatorRepository.save(elevator);
        log.info("Лифт с ID {} успешно создан", savedElevator.getId());
        return elevatorMapper.toResponse(savedElevator);
    }

    /**
     * Получение списка всех лифтов.
     *
     * @return Список всех лифтов.
     */
    public List<ElevatorResponse> getAllElevators() {
        log.info("Запрос списка всех лифтов");

        List<ElevatorResponse> responses = elevatorRepository.findAll()
            .stream()
            .map(elevatorMapper::toResponse)
            .collect(Collectors.toList());

        log.info("Найдено {} лифтов", responses.size());
        return responses;
    }

    /**
     * Получение лифта по его ID.
     *
     * @param id ID лифта.
     * @return Найденный лифт.
     * @throws ElevatorNotFoundException если лифт не найден.
     */
    public ElevatorResponse getElevatorById(Long id) {
        log.info("Запрос лифта с ID: {}", id);

        Elevator elevator = elevatorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Лифт с ID {} не найден", id);
                return new ElevatorNotFoundException("Лифт с ID " + id + " не найден");
            });

        log.info("Лифт с ID {} найден", id);
        return elevatorMapper.toResponse(elevator);
    }

    /**
     * Обновление лифта по его ID.
     *
     * @param id ID лифта.
     * @param request DTO с новыми данными лифта.
     * @return Обновлённый лифт.
     * @throws ElevatorNotFoundException если лифт не найден.
     */
    public ElevatorResponse updateElevator(Long id, ElevatorRequest request) {
        log.info("Попытка обновления лифта с ID: {}", id);

        Elevator elevator = elevatorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Лифт с ID {} не найден", id);
                return new ElevatorNotFoundException("Лифт с ID " + id + " не найден");
            });

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        elevator.setName(request.getName());
        elevator.setEntrance(entrance);
        elevator.setFloor(request.getFloor());

        Elevator updatedElevator = elevatorRepository.save(elevator);
        log.info("Лифт с ID {} успешно обновлён", updatedElevator.getId());
        return elevatorMapper.toResponse(updatedElevator);
    }

    /**
     * Удаление лифта по его ID.
     *
     * @param id ID лифта.
     * @throws ElevatorNotFoundException если лифт не найден.
     */
    public void deleteElevator(Long id) {
        log.info("Попытка удаления лифта с ID: {}", id);

        Elevator elevator = elevatorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Лифт с ID {} не найден", id);
                return new ElevatorNotFoundException("Лифт с ID " + id + " не найден");
            });

        elevatorRepository.delete(elevator);
        log.info("Лифт с ID {} успешно удалён", id);
    }
}
