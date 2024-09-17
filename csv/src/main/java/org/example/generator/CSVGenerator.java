package org.example.generator;

import org.example.annotation.CSVColumn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public final class CSVGenerator {
    private Map<String, String> settings;

    public CSVGenerator(Map<String, String> settings) {
        this.settings = settings;
    }

    public <T> void writeDataToFile(List<T> data, String path) {
        var fielPath = Path.of(path);
        try {
            writeHeaders(fielPath, data.getFirst());

            for (var obj : data) {
                writeObjToFile(fielPath, obj);
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void writeHeaders(Path path, T obj) throws IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                var annotation = field.getAnnotation(CSVColumn.class);
                sb.append(annotation.header()).append(", ");
            }
        }

        Files.writeString(path, sb.substring(0, sb.length() - 1) + System.lineSeparator(),
                StandardOpenOption.CREATE);
    }

    private <T> void writeObjToFile(Path path, T obj) throws IllegalAccessException, IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                field.setAccessible(true);
                sb.append(field.get(obj)).append(", ");
            }
        }
        Files.writeString(path, sb.substring(0, sb.length() - 1) + System.lineSeparator(),
                StandardOpenOption.APPEND);
    }
}
