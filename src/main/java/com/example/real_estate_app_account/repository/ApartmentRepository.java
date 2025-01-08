package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Apartment.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
