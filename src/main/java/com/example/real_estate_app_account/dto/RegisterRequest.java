package com.example.real_estate_app_account.dto;

import lombok.Data;

/**
 * DTO для запроса на регистрацию нового пользователя.
 * Содержит данные, которые клиент отправляет при регистрации.
 */
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
