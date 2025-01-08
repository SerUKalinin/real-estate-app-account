package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Address.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByStreetAndNumberHouseAndCity(String street, String numberHouse, String city);
    Address findByStreetAndNumberHouseAndCity(String street, String numberHouse, String city);
}
