package com.example.real_estate_app_account.service.aspect;

import com.example.real_estate_app_account.dto.ApartmentRequest;
import com.example.real_estate_app_account.dto.ApartmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
@Slf4j
public class ApartmentServiceLoggingAspect {

    @Pointcut("execution(* com.example.real_estate_app_account.service.ApartmentService.createApartment(..)) && args(request)")
    public void createApartmentPointCut(ApartmentRequest request) { }

    @Before("createApartmentPointCut(request)")
    public void logBeforeCreateApartment(ApartmentRequest request) {
        log.info("Попытка создания квартиры с номером: {}", request.getNumber());
        log.info("Детали запроса: этаж ID {}, здание ID {}, площадь {}",
            request.getFloorId(), request.getBuildingId(), request.getArea());
    }

    @AfterReturning(
        pointcut = "execution(* com.example.real_estate_app_account.service.ApartmentService.createApartment(..))",
        returning = "response")
    public void logAfterCreateApartment(ApartmentResponse response) {
        log.info("Квартира с ID {} успешно создана", response.getId());
    }

    @Pointcut("execution(* com.example.real_estate_app_account.service.ApartmentService.getAllApartments(..))")
    public void getAllApartmentsPointCut() { }

    @Before("getAllApartmentsPointCut()")
    public void logBeforeGetAllApartments() {
        log.info("Запрос списка всех квартир");
    }

    @AfterReturning(
        pointcut = "getAllApartmentsPointCut()",
        returning = "response")
    public void logAfterGetAllApartments(List<ApartmentResponse> response) {
        log.info("Получено {} квартир", response.size());
    }

    @Pointcut("execution(* com.example.real_estate_app_account.service.ApartmentService.getApartmentById(..)) && args(id)")
    public void getApartmentByIdPointCut(Long id) { }

    @Before("getApartmentByIdPointCut(id)")
    public void logBeforeGetApartmentById(Long id) {
        log.info("Запрос квартиры с ID: {}", id);
    }

    @Pointcut("execution(* com.example.real_estate_app_account.service.ApartmentService.updateApartment(..)) && args(id, request)")
    public void updateApartmentPointCut(Long id, ApartmentRequest request) { }

    @Before("updateApartmentPointCut(id, request)")
    public void logBeforeUpdateApartment(Long id, ApartmentRequest request) {
        log.info("Попытка обновления квартиры с ID: {}", id);
        log.info("Новые данные: номер {}, площадь {}, этаж ID {}, здание ID {}",
            request.getNumber(), request.getArea(), request.getFloorId(), request.getBuildingId());
    }

    @Pointcut("execution(* com.example.real_estate_app_account.service.ApartmentService.deleteApartment(..)) && args(id)")
    public void deleteApartmentPointCut(Long id) { }

    @Before("deleteApartmentPointCut(id)")
    public void logBeforeDeleteApartment(Long id) {
        log.info("Попытка удаления квартиры с ID: {}", id);
    }

    @AfterReturning("deleteApartmentPointCut(id)")
    public void logAfterDeleteApartment(Long id) {
        log.info("Квартира с ID {} успешно удалена", id);
    }
}
