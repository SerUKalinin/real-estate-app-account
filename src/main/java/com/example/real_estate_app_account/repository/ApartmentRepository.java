package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
