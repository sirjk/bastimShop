package com.company.shopBastim.exceptions;

public class EmailTakenException extends Exception {
    public EmailTakenException(Long id){
        System.out.println("Email already taken on: " + id.toString());
    }
}
