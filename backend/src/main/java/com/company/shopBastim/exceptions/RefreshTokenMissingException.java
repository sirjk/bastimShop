package com.company.shopBastim.exceptions;

public class RefreshTokenMissingException extends Exception {
    public RefreshTokenMissingException(){
        System.out.println("Refresh Token missing");
    }
}
