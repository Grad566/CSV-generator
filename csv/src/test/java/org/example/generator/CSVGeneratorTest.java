package org.example.generator;

import org.example.exception.CSVGeneratorException;
import org.example.test.classes.PersonForTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class CSVGeneratorTest {
    @Test
    public void testWriteDataToFile() throws IOException, CSVGeneratorException {
        CSVGenerator csvGenerator = new CSVGenerator(new HashMap<>());
        PersonForTest person1 = new PersonForTest("John", 29);
        PersonForTest person2 = new PersonForTest("Alex", 5);
        List<PersonForTest> list = new ArrayList<>(List.of(person1, person2));
        String path = "src/test/test_out.txt";

        csvGenerator.writeDataToFile(list, path);

        Path path1 = Path.of(path);
        List<String> res = Files.readAllLines(path1);
        assertEquals("name, age", res.getFirst());
        assertEquals("John, 29", res.get(1));
        assertEquals("Alex, 5", res.get(2));

        Files.delete(path1);
    }

    @Test
    public void testWriteDataToFileWithSettings() throws IOException, CSVGeneratorException {
        CSVGenerator csvGenerator = new CSVGenerator(new HashMap<>(Map.of(
                "delimiter", ": ",
                "includingHeaders", "false"
        )));
        PersonForTest person1 = new PersonForTest("John", 29);
        PersonForTest person2 = new PersonForTest("Alex", 5);
        List<PersonForTest> list = new ArrayList<>(List.of(person1, person2));
        String path = "src/test/test_out2.txt";

        csvGenerator.writeDataToFile(list, path);

        Path path1 = Path.of(path);
        var res = Files.readAllLines(path1);
        assertEquals("John: 29", res.getFirst());
        assertEquals("Alex: 5", res.get(1));

        Files.delete(path1);
    }
}