package com.mleekko.spreadsheet.ex;

public class CyclicReferenceException extends RuntimeException {

    public CyclicReferenceException(String message) {
        super(message);
    }
}
