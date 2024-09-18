package org.example.generator;

import org.example.annotation.CSVColumn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public final class CSVGenerator {
    private String delimiter;
    private boolean includingHeaders;

    public CSVGenerator(Map<String, String> settings) {
        delimiter = settings.getOrDefault("delimiter", ", ");
        includingHeaders = Boolean.parseBoolean(settings.getOrDefault("includingHeaders", "true"));
    }

    public <T> void writeDataToFile(List<T> data, String path) {
        try {
            var fielPath = Path.of(path);

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
            throw new RuntimeException("Error when writing data to file " + path, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error of accessing object fields when writing to a file " + path, e);
        }
    }

    private <T> void writeHeaders(Path path, T obj) throws IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                var annotation = field.getAnnotation(CSVColumn.class);
                sb.append(annotation.header()).append(delimiter);
            }
        }

        Files.writeString(path, sb.substring(0, sb.length() - 2) + System.lineSeparator(),
                StandardOpenOption.APPEND);
    }

    private <T> void writeObjToFile(Path path, T obj) throws IllegalAccessException, IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                field.setAccessible(true);
                sb.append(field.get(obj)).append(delimiter);
            }
        }
        Files.writeString(path, sb.substring(0, sb.length() - 2) + System.lineSeparator(),
                StandardOpenOption.APPEND);
    }
}
