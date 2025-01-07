package com.example.real_estate_app_account.dto;

import lombok.Data;

/**
 * DTO для запроса на аутентификацию пользователя.
 * Содержит данные, которые клиент отправляет для входа в систему.
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
