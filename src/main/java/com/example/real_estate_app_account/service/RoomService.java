package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.RoomRequest;
import com.example.real_estate_app_account.dto.RoomResponse;
import com.example.real_estate_app_account.exception.*;
import com.example.real_estate_app_account.mapper.RoomMapper;
import com.example.real_estate_app_account.model.*;
import com.example.real_estate_app_account.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final ApartmentRepository apartmentRepository;
    private final CorridorRepository corridorRepository;
    private final FloorRepository floorRepository;
    private final EntranceRepository entranceRepository;
    private final BuildingRepository buildingRepository;
    private final RoomMapper roomMapper;

    public RoomResponse createRoom(RoomRequest request) {
        log.info("Создание комнаты: {}", request.getName());

        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
            .orElseThrow(() -> new ApartmentNotFoundException("Квартира с ID " + request.getApartmentId() + " не найдена"));

        Corridor corridor = corridorRepository.findById(request.getCorridorId())
            .orElseThrow(() -> new CorridorNotFoundException("Коридор с ID " + request.getCorridorId() + " не найден"));

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден"));

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено"));

        Room room = roomMapper.toEntity(request);
        room.setApartment(apartment);
        room.setCorridor(corridor);
        room.setFloor(floor);
        room.setEntrance(entrance);
        room.setBuilding(building);

        Room savedRoom = roomRepository.save(room);
        log.info("Комната с ID {} успешно создана", savedRoom.getId());
        return roomMapper.toResponse(savedRoom);
    }

    public List<RoomResponse> getAllRooms() {
        log.info("Получение списка всех комнат");
        return roomRepository.findAll()
            .stream()
            .map(roomMapper::toResponse)
            .toList();
    }

    public RoomResponse getRoomById(Long id) {
        log.info("Получение комнаты с ID {}", id);

        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Комната с ID " + id + " не найдена"));

        return roomMapper.toResponse(room);
    }

    public RoomResponse updateRoom(Long id, RoomRequest request) {
        log.info("Обновление комнаты с ID {}", id);

        Room room = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Комната с ID " + id + " не найдена"));

        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
            .orElseThrow(() -> new ApartmentNotFoundException("Квартира с ID " + request.getApartmentId() + " не найдена"));

        Corridor corridor = corridorRepository.findById(request.getCorridorId())
            .orElseThrow(() -> new CorridorNotFoundException("Коридор с ID " + request.getCorridorId() + " не найден"));

        Floor floor = floorRepository.findById(request.getFloorId())
            .orElseThrow(() -> new FloorNotFoundException("Этаж с ID " + request.getFloorId() + " не найден"));

        Entrance entrance = entranceRepository.findById(request.getEntranceId())
            .orElseThrow(() -> new EntranceNotFoundException("Подъезд с ID " + request.getEntranceId() + " не найден"));

        Building building = buildingRepository.findById(request.getBuildingId())
            .orElseThrow(() -> new BuildingNotFoundException("Здание с ID " + request.getBuildingId() + " не найдено"));

        room.setName(request.getName());
        room.setArea(request.getArea());
        room.setApartment(apartment);
        room.setCorridor(corridor);
        room.setFloor(floor);
        room.setEntrance(entrance);
        room.setBuilding(building);

        Room updatedRoom = roomRepository.save(room);
        log.info("Комната с ID {} успешно обновлена", updatedRoom.getId());
        return roomMapper.toResponse(updatedRoom);
    }

    public void deleteRoom(Long id) {
        log.info("Удаление комнаты с ID {}", id);
        roomRepository.deleteById(id);
        log.info("Комната с ID {} успешно удалена", id);
    }
}
