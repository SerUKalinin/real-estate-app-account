package com.example.real_estate_app_account.service;

import com.example.real_estate_app_account.dto.AuthResponse;
import com.example.real_estate_app_account.dto.LoginRequest;
import com.example.real_estate_app_account.dto.RegisterRequest;
import com.example.real_estate_app_account.entity.Role;
import com.example.real_estate_app_account.model.User;
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
        if (userRepository.findByUsername(request.getUsername()).isPresent() ||
            userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Имя пользователя или адрес электронной почты уже существуют.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    /**
     * Авторизация пользователя.
     *
     * @param request DTO с данными для авторизации.
     * @return Токен авторизации.
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Неверное имя пользователя или пароль."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Неверное имя пользователя или пароль.");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
