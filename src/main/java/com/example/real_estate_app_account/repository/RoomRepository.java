package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
