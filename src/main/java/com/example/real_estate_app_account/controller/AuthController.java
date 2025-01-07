package com.example.real_estate_app_account.controller;

import com.example.real_estate_app_account.dto.AuthResponse;
import com.example.real_estate_app_account.dto.LoginRequest;
import com.example.real_estate_app_account.dto.RegisterRequest;
import com.example.real_estate_app_account.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления аутентификацией пользователей.
 * Предоставляет API для регистрации и входа в систему.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j // Аннотация для логирования
public class AuthController {
    private final AuthService authService;

    /**
     * Регистрация нового пользователя.
     *
     * @param request DTO с данными для регистрации пользователя.
     * @return ResponseEntity с сообщением об успешной регистрации и статусом 200 OK.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        log.info("Попытка регистрации пользователя с именем: {}", request.getUsername());
        authService.register(request);
        log.info("Пользователь с именем '{}' успешно зарегистрирован.", request.getUsername());
        return ResponseEntity.ok("Пользователь успешно зарегистрировался.");
    }

    /**
     * Аутентификация пользователя.
     *
     * @param request DTO с данными для входа в систему.
     * @return ResponseEntity с данными для аутентификации (JWT токен) и статусом 200 OK.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Попытка входа пользователя с именем: {}", request.getUsername());
        AuthResponse response = authService.login(request);
        log.info("Пользователь с именем '{}' успешно авторизован.", request.getUsername());
        return ResponseEntity.ok(response);
    }
}
