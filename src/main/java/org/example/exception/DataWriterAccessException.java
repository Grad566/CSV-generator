package org.example.exception;

public class DataWriterAccessException extends DataWriterException {
    public DataWriterAccessException(String path, Throwable cause) {
        super("Error accessing object fields when writing to a file: " + path, cause);
    }
}
