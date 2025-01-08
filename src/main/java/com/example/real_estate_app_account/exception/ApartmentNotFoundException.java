package com.example.real_estate_app_account.exception;

public class ApartmentNotFoundException extends RuntimeException{
    public ApartmentNotFoundException(String message) {
        super(message);
    }
}
