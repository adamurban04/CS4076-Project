package org.example;

public class IncorrectActionException extends Exception {
    public IncorrectActionException() {
        super("Incorrect Action Detected");
    }

    public IncorrectActionException(String message) {
        super(message);
    }
}
