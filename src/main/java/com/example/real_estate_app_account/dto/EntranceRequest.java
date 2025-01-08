package com.example.real_estate_app_account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса создания или обновления подъезда.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntranceRequest {
    private String entranceName;
    private Long buildingId; // ID связанного здания
}
