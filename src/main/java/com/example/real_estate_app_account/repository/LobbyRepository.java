package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
}