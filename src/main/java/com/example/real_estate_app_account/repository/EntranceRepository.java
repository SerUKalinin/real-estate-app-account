package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Entrance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Entrance.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface EntranceRepository extends JpaRepository<Entrance, Long> {
}
