package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
