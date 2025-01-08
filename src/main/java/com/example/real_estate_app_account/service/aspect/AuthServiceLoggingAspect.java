package com.example.real_estate_app_account.service.aspect;

import com.example.real_estate_app_account.dto.LoginRequest;
import com.example.real_estate_app_account.dto.RegisterRequest;
import com.example.real_estate_app_account.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AuthServiceLoggingAspect {

    @Pointcut("execution(* com.example.real_estate_app_account.service.AuthService.register(..)) && args(request)")
    public void registerPointCut(RegisterRequest request) { }

    @Before("registerPointCut(request)")
    public void logBeforeRegister(RegisterRequest request) {
        log.info("Попытка регистрации пользователя с именем: {}", request.getUsername());
    }

    @AfterReturning("registerPointCut(request)")
    public void logAfterRegister(RegisterRequest request) {
        log.info("Пользователь с именем '{}' успешно зарегистрирован.", request.getUsername());
    }

    @Pointcut("execution(* com.example.real_estate_app_account.service.AuthService.login(..)) && args(request)")
    public void loginPointCut(LoginRequest request) { }

    @Before("loginPointCut(request)")
    public void logBeforeLogin(LoginRequest request) {
        log.info("Попытка входа пользователя с именем: {}", request.getUsername());
    }

    @AfterReturning(pointcut = "loginPointCut(request)", returning = "response")
    public void logAfterLogin(LoginRequest request, AuthResponse response) {
        log.info("Пользователь '{}' успешно авторизован. Токен сгенерирован: {}", request.getUsername(), response.getToken());
    }
}
