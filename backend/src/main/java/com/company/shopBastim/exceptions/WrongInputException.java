package com.company.shopBastim.exceptions;

public class WrongInputException extends Exception {
    public String wrongInputMessage;
    public WrongInputException() {System.out.println("Wrong input");}
    public WrongInputException(String message) {System.out.println("Wrong input:" + message); wrongInputMessage = message;}
}
