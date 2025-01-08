package com.example.real_estate_app_account.exception;

public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(String message) {
        super(message);
    }
}
