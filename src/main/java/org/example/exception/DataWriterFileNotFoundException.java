package org.example.exception;

public class DataWriterFileNotFoundException extends DataWriterException {
    public DataWriterFileNotFoundException(String path) {
        super("File not found:" + path);
    }
}
