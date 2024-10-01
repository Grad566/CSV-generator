package org.example.exception;

public class CSVAccessException extends CSVGeneratorException {
    public CSVAccessException(String path, Throwable cause) {
        super("Error accessing object fields when writing to a file: " + path, cause);
    }
}
