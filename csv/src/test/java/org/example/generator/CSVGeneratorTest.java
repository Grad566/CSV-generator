package org.example.generator;

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
    public void testWriteDataToFile() throws IOException {
        var csvGenerator = new CSVGenerator(new HashMap<>());
        var person1 = new PersonForTest("John", 29);
        var person2 = new PersonForTest("Alex", 5);
        var list = new ArrayList<PersonForTest>(List.of(person1, person2));
        var path = "src/test/test_out.txt";

        csvGenerator.writeDataToFile(list, path);

        var res = Files.readAllLines(Path.of(path));
        assertEquals("name, age", res.getFirst());
        assertEquals("John, 29", res.get(1));
        assertEquals("Alex, 5", res.get(2));

        Files.delete(Path.of(path));
    }

    @Test
    public void testWriteDataToFileWithSettings() throws IOException {
        var csvGenerator = new CSVGenerator(new HashMap<String, String>(Map.of(
                "delimiter", ": ",
                "includingHeaders", "false"
        )));
        var person1 = new PersonForTest("John", 29);
        var person2 = new PersonForTest("Alex", 5);
        var list = new ArrayList<PersonForTest>(List.of(person1, person2));
        var path = "src/test/test_out2.txt";

        csvGenerator.writeDataToFile(list, path);

        var res = Files.readAllLines(Path.of(path));
        assertEquals("John: 29", res.getFirst());
        assertEquals("Alex: 5", res.get(1));

        Files.delete(Path.of(path));
    }
}