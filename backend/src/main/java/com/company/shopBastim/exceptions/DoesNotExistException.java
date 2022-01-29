package com.company.shopBastim.exceptions;

public class DoesNotExistException extends Exception {
    public DoesNotExistException(String origin){
        System.out.println(origin + " with provided id not found in database");
    }
}
