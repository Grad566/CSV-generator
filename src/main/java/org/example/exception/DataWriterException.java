package org.example.exception;

public class DataWriterException extends Exception {
    public DataWriterException(String message) {
        super(message);
    }

    public DataWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
