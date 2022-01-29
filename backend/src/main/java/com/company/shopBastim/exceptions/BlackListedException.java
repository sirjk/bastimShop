package com.company.shopBastim.exceptions;

public class BlackListedException extends Exception {
    public BlackListedException(){
        System.out.println("Token blacklisted");
    }

}
