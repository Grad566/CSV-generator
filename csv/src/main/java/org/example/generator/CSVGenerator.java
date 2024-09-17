package org.example.generator;

import org.example.annotation.CSVColumn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class CSVGenerator {
    private Map<String, String> settings;

    public CSVGenerator(Map<String, String> settings) {
        this.settings = settings;
    }

    public <T> void writeObjToFile(List<T> data, String path) {
        try (var writer = new BufferedWriter(new FileWriter(path))) {
            writeHeaders(writer, data.getFirst());

            for (var obj : data) {
                writeDataToFile(writer, obj);
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void writeHeaders(BufferedWriter writer, T obj) throws IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                var annotation = field.getAnnotation(CSVColumn.class);
                sb.append(annotation.header()).append(", ");
            }
        }

        writer.write(sb.substring(0, sb.length() - 1));
        writer.newLine();
    }

    private <T> void writeDataToFile(BufferedWriter writer, T obj) throws IllegalAccessException, IOException {
        var fields = obj.getClass().getDeclaredFields();
        var sb = new StringBuilder();

        for (var field : fields) {
            if (field.isAnnotationPresent(CSVColumn.class)) {
                field.setAccessible(true);
                sb.append(field.get(obj)).append(", ");
            }
        }
        writer.write(sb.substring(0, sb.length() - 1));
        writer.newLine();
    }
}
