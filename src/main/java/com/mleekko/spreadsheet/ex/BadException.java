package com.mleekko.spreadsheet.ex;

public class BadException extends RuntimeException {

    public BadException(String message) {
        super(message);
    }

    public static BadException die(String message) {
        return new BadException(message);
    }
}
