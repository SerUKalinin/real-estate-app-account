package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Building.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
