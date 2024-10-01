package org.example.exception;

public class CSVGeneratorException extends Exception {
    public CSVGeneratorException(String message) {
        super(message);
    }

    public CSVGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
