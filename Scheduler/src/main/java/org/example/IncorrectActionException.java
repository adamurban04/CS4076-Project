package org.example;

public class IncorrectActionException
{
    String message;
    public IncorrectActionException() {
        this.message = "Too many Stuff!";
    }
    public IncorrectActionException(String str) {
        this.message = str;
    }


    public String getTooManyMessage(){
        return message;
    }

}
