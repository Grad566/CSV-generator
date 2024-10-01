package org.example.generator;

import lombok.Getter;
import lombok.Setter;
import org.example.annotation.CSVColumn;
import org.example.exception.CSVAccessException;
import org.example.exception.CSVFileNotFoundException;
import org.example.exception.CSVGeneratorException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public final class CSVGenerator {
    private String delimiter;
    private boolean includingHeaders;

    public CSVGenerator(Map<String, String> settings) {
        delimiter = settings.getOrDefault("delimiter", ", ");
        includingHeaders = Boolean.parseBoolean(settings.getOrDefault("includingHeaders", "true"));
    }

    public <T> void writeDataToFile(T[] data, String path) throws CSVGeneratorException {
        writeDataToFile(List.of(data), path);
    }

    public <T> void writeDataToFile(List<T> data, String path) throws CSVGeneratorException {
        try {
            Path fielPath = Path.of(path);

            if (!Files.exists(fielPath)) {
                Files.createFile(fielPath);
            }

            if (includingHeaders) {
                writeHeaders(fielPath, data.getFirst());
            }

            for (var obj : data) {
                writeObjToFile(fielPath, obj);
            }
        } catch (IOException e) {
            throw new CSVFileNotFoundException(path);
        } catch (IllegalAccessException e) {
            throw new CSVAccessException(path, e);
        }
    }

    private <T> void writeHeaders(Path path, T obj) throws CSVGeneratorException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                CSVColumn annotation = field.getAnnotation(CSVColumn.class);
                sb.append(annotation.header()).append(delimiter);
            }
        }

        try {
            Files.writeString(path, sb.substring(0, sb.length() - 2) + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new CSVGeneratorException("Error writing headers to file: " + path, e);
        }
    }

    private <T> void writeObjToFile(Path path, T obj) throws CSVGeneratorException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                field.setAccessible(true);
                sb.append(field.get(obj)).append(delimiter);
            }
        }
        try {
            Files.writeString(path, sb.substring(0, sb.length() - 2) + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new CSVGeneratorException("Error writing object to file: " + path, e);
        }
    }
}
