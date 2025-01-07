package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.AuthResponse;
import com.example.real_estate_app_account.dto.LoginRequest;
import com.example.real_estate_app_account.dto.RegisterRequest;
import com.example.real_estate_app_account.entity.Role;
import com.example.real_estate_app_account.entity.User;
import com.example.real_estate_app_account.repository.UserRepository;
import com.example.real_estate_app_account.service.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Регистрация нового пользователя.
     *
     * @param request DTO с данными для регистрации.
     */
    public void register(RegisterRequest request) {
        log.info("Попытка регистрации пользователя с именем: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent() ||
            userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Ошибка регистрации: имя пользователя или email уже существует.");
            throw new IllegalArgumentException("Имя пользователя или адрес электронной почты уже существуют.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        log.info("Пользователь с именем '{}' успешно зарегистрирован.", request.getUsername());
    }

    /**
     * Авторизация пользователя.
     *
     * @param request DTO с данными для авторизации.
     * @return Токен авторизации.
     */
    public AuthResponse login(LoginRequest request) {
        log.info("Попытка входа пользователя с именем: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> {
                log.warn("Ошибка входа: неверное имя пользователя или пароль.");
                return new IllegalArgumentException("Неверное имя пользователя или пароль.");
            });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Ошибка входа: неверный пароль для пользователя '{}'.", request.getUsername());
            throw new IllegalArgumentException("Неверное имя пользователя или пароль.");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        log.info("Пользователь '{}' успешно авторизован. Токен сгенерирован.", request.getUsername());

        return new AuthResponse(token);
    }
}
