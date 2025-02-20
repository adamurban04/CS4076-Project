package org.example;

public class IncorrectActionException {
    String message;

    public IncorrectActionException() {
        this.message = "Incorrect Action Detected";
    }

    public IncorrectActionException(String str) {
        this.message = str;
    }
}