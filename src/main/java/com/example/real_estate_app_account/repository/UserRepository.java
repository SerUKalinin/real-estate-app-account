package com.example.real_estate_app_account.repository;

import com.example.real_estate_app_account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью User.
 * Использует Spring Data JPA для выполнения операций с базой данных.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Поиск пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Optional<User> - Оборачивает пользователя, если найден, или пустой объект, если не найден.
     */
    Optional<User> findByUsername(String username);

    /**
     * Поиск пользователя по электронной почте.
     *
     * @param email Электронная почта пользователя.
     * @return Optional<User> - Оборачивает пользователя, если найден, или пустой объект, если не найден.
     */
    Optional<User> findByEmail(String email);
}
