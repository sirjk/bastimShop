package com.company.shopBastim.exceptions;

public class EmptyResourceException extends Exception {
    public EmptyResourceException(){
        System.out.println("The resource you try to reach is empty");
    }
}
