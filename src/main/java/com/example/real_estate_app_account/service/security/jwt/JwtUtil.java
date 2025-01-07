package com.example.real_estate_app_account.service.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Утилита для работы с JWT (JSON Web Token).
 * Содержит методы для генерации, извлечения данных и проверки токенов.
 */
@Component
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "2eaf9c35a0e943cf81f61bfe6f8f80d287d0db1d7f520b0a02d8d7d90768bbf4";
    private static final long EXPIRATION_TIME = 86400000;
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Генерация JWT токена для пользователя.
     *
     * @param username Имя пользователя, которое будет записано в токен.
     * @return Сгенерированный JWT токен.
     */
    public String generateToken(String username) {
        log.info("Генерация токена для пользователя: {}", username);

        String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        log.info("Токен для пользователя '{}' успешно сгенерирован.", username);

        return token;
    }

    /**
     * Извлечение имени пользователя из JWT токена.
     *
     * @param token JWT токен.
     * @return Имя пользователя, содержащееся в токене.
     */
    public String extractUsername(String token) {
        try {
            log.info("Извлечение имени пользователя из токена.");

            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Ошибка при извлечении имени пользователя из токена: {}", e.getMessage());
            throw new IllegalArgumentException("Ошибка при извлечении имени пользователя из токена", e);
        }
    }

    /**
     * Проверка валидности JWT токена.
     * Проверяет подпись и срок действия токена.
     *
     * @param token JWT токен.
     * @return true, если токен валиден, иначе false.
     */
    public boolean validateToken(String token) {
        try {
            log.info("Проверка валидности токена.");

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("Токен валиден.");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Ошибка при проверке токена: {}", e.getMessage());
            return false;
        }
    }
}
