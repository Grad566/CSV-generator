package org.example.service;

import org.example.annotation.CSVExportable;
import org.example.exception.DataWriterException;
import org.example.generator.CSVGenerator;
import org.example.generator.DataWriter;

import java.util.HashMap;
import java.util.List;

public class DataWriterService {
    private DataWriter dataWriter;

    public <T> void writeData(List<T> data, String path, HashMap<String, String> settings)
            throws DataWriterException {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be empty");
        }

        Class<?> clazz = data.getFirst().getClass();

        if (clazz.isAnnotationPresent(CSVExportable.class)) {
            dataWriter = new CSVGenerator(settings);
            dataWriter.writeDataToFile(data, path);
        } else {
            throw new UnsupportedOperationException("No exportable format found for class: " + clazz.getName());
        }
    }

    public <T> void writeData(T[] data, String path, HashMap<String, String> settings)
            throws DataWriterException {
        writeData(List.of(data), path, settings);
    }
}
