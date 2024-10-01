package org.example.exception;

public class CSVFileNotFoundException extends CSVGeneratorException {
    public CSVFileNotFoundException(String path) {
        super("File not found:" + path);
    }
}
