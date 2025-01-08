package com.example.real_estate_app_account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для ответа с информацией о подъезде.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntranceResponse {
    private Long id;
    private String entranceName;
    private Long buildingId;
    private String buildingName; // Название связанного здания
}
