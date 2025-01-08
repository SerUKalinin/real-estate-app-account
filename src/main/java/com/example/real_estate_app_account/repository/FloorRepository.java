package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Floor.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface FloorRepository extends JpaRepository<Floor, Long> {
}
