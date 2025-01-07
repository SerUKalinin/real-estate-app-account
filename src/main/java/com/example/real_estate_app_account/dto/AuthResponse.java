package com.example.real_estate_app_account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для ответа на запрос аутентификации.
 * Содержит токен, который возвращается клиенту после успешной аутентификации.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
