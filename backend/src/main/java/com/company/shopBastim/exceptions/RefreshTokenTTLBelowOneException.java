package com.company.shopBastim.exceptions;

public class RefreshTokenTTLBelowOneException extends Exception {
    public RefreshTokenTTLBelowOneException(){
        System.out.println("Refresh Token TTL is below 1");
    }
}
