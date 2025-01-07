package com.example.real_estate_app_account.service.security;

import com.example.real_estate_app_account.entity.User;
import com.example.real_estate_app_account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для загрузки данных пользователя для аутентификации.
 * Реализует интерфейс UserDetailsService, который используется Spring Security для работы с данными пользователя.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загрузка пользователя по имени пользователя.
     * Используется для аутентификации пользователя.
     *
     * @param username Имя пользователя для поиска.
     * @return UserDetails объект с данными пользователя.
     * @throws UsernameNotFoundException Если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Попытка загрузить пользователя с именем '{}'.", username);

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                log.error("Пользователь с именем '{}' не найден.", username);
                return new UsernameNotFoundException("User not found");
            });
        log.info("Пользователь с именем '{}' успешно найден.", username);


        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
    }
}
