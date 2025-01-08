package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.LobbyRequest;
import com.example.real_estate_app_account.dto.LobbyResponse;
import com.example.real_estate_app_account.exception.BuildingNotFoundException;
import com.example.real_estate_app_account.exception.EntranceNotFoundException;
import com.example.real_estate_app_account.exception.ElevatorNotFoundException;
import com.example.real_estate_app_account.model.Building;
import com.example.real_estate_app_account.model.Entrance;
import com.example.real_estate_app_account.model.Elevator;
import com.example.real_estate_app_account.model.Lobby;
import com.example.real_estate_app_account.repository.BuildingRepository;
import com.example.real_estate_app_account.repository.EntranceRepository;
import com.example.real_estate_app_account.repository.ElevatorRepository;
import com.example.real_estate_app_account.repository.LobbyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LobbyService {

    private final LobbyRepository lobbyRepository;
    private final ElevatorRepository elevatorRepository;
    private final EntranceRepository entranceRepository;
    private final BuildingRepository buildingRepository;

    public LobbyResponse createLobby(LobbyRequest request) {
        log.info("Попытка создания лифтового холла: {}", request);

        Elevator elevator = elevatorRepository.findById(request.getElevatorId())
            .orElseThrow(() -> {
                log.error("Лифт с ID {} не найден", request.getElevatorId());
                return new ElevatorNotFoundException("Лифт с ID " + request.getElevatorId() + " не найден");
            });

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        Lobby lobby = new Lobby();
        lobby.setLobbyName(request.getLobbyName());
        lobby.setElevator(elevator);
        lobby.setEntrance(entrance);
        lobby.setFloor(request.getFloor());
        lobby.setBuilding(building);

        Lobby savedLobby = lobbyRepository.save(lobby);
        log.info("Лифтовый холл с ID {} успешно создан", savedLobby.getId());
        return mapToResponse(savedLobby);
    }

    public List<LobbyResponse> getAllLobbies() {
        log.info("Запрос списка всех лифтовых холлов");
        List<LobbyResponse> responses = lobbyRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        log.info("Найдено {} лифтовых холлов", responses.size());
        return responses;
    }

    public LobbyResponse getLobbyById(Long id) {
        log.info("Запрос лифтового холла с ID {}", id);
        Lobby lobby = lobbyRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Лифтовый холл с ID {} не найден", id);
                return new RuntimeException("Лифтовый холл с ID " + id + " не найден");
            });
        log.info("Лифтовый холл с ID {} найден", id);
        return mapToResponse(lobby);
    }

    public LobbyResponse updateLobby(Long id, LobbyRequest request) {
        log.info("Попытка обновления лифтового холла с ID {}", id);

        Lobby lobby = lobbyRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Лифтовый холл с ID {} не найден", id);
                return new RuntimeException("Лифтовый холл с ID " + id + " не найден");
            });

        Elevator elevator = elevatorRepository.findById(request.getElevatorId())
            .orElseThrow(() -> {
                log.error("Лифт с ID {} не найден", request.getElevatorId());
                return new ElevatorNotFoundException("Лифт с ID " + request.getElevatorId() + " не найден");
            });

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> {
                log.error("Подъезд с ID {} не найден", request.getEntranceId());
                return new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден");
            });

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> {
                log.error("Здание с ID {} не найдено", request.getBuildingId());
                return new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено");
            });

        lobby.setLobbyName(request.getLobbyName());
        lobby.setElevator(elevator);
        lobby.setEntrance(entrance);
        lobby.setFloor(request.getFloor());
        lobby.setBuilding(building);

        Lobby updatedLobby = lobbyRepository.save(lobby);
        log.info("Лифтовый холл с ID {} успешно обновлён", updatedLobby.getId());
        return mapToResponse(updatedLobby);
    }

    public void deleteLobby(Long id) {
        log.info("Попытка удаления лифтового холла с ID {}", id);
        if (!lobbyRepository.existsById(id)) {
            log.error("Лифтовый холл с ID {} не найден", id);
            throw new RuntimeException("Лифтовый холл с ID " + id + " не найден");
        }
        lobbyRepository.deleteById(id);
        log.info("Лифтовый холл с ID {} успешно удалён", id);
    }

    private LobbyResponse mapToResponse(Lobby lobby) {
        LobbyResponse response = new LobbyResponse();
        response.setId(lobby.getId());
        response.setLobbyName(lobby.getLobbyName());
        response.setElevatorId(lobby.getElevator().getId());
        response.setEntranceId(lobby.getEntrance().getId());
        response.setFloor(lobby.getFloor());
        response.setBuildingId(lobby.getBuilding().getId());
        return response;
    }
}
