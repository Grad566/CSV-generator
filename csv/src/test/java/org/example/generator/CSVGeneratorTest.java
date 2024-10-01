package org.example.generator;

import org.example.exception.CSVFileNotFoundException;
import org.example.exception.CSVGeneratorException;
import org.example.test.classes.PersonForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private CSVGenerator csvGenerator;
    private String testFilePath;
    private String testFilePathWithSettings;

    @BeforeEach
    public void setUp() {
        csvGenerator = new CSVGenerator(new HashMap<>());
        testFilePath = "src/test/test_out.txt";
        testFilePathWithSettings = "src/test/test_out2.txt";
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(testFilePath));
        Files.deleteIfExists(Path.of(testFilePathWithSettings));
    }

    @Test
    public void testWriteDataToFile() throws IOException, CSVGeneratorException {
        PersonForTest person1 = new PersonForTest("John", 29);
        PersonForTest person2 = new PersonForTest("Alex", 5);
        List<PersonForTest> list = new ArrayList<>(List.of(person1, person2));

        csvGenerator.writeDataToFile(list, testFilePath);

        List<String> res = Files.readAllLines(Path.of(testFilePath));
        assertEquals("name, age", res.get(0));
        assertEquals("John, 29", res.get(1));
        assertEquals("Alex, 5", res.get(2));
    }

    @Test
    public void testWriteDataToFileWithSettings() throws IOException, CSVGeneratorException {
        csvGenerator = new CSVGenerator(new HashMap<>(Map.of(
                "delimiter", ": ",
                "includingHeaders", "false"
        )));
        PersonForTest person1 = new PersonForTest("John", 29);
        PersonForTest person2 = new PersonForTest("Alex", 5);
        List<PersonForTest> list = new ArrayList<>(List.of(person1, person2));

        csvGenerator.writeDataToFile(list, testFilePathWithSettings);

        List<String> res = Files.readAllLines(Path.of(testFilePathWithSettings));
        assertEquals("John: 29", res.get(0));
        assertEquals("Alex: 5", res.get(1));
    }

    @Test
    public void testWriteArrayToFile() throws CSVGeneratorException, IOException {
        PersonForTest[] people = {
                new PersonForTest("Alice", 30),
                new PersonForTest("Bob", 25)
        };

        csvGenerator.writeDataToFile(people, testFilePath);

        List<String> res = Files.readAllLines(Path.of(testFilePath));
        assertEquals("name, age", res.get(0));
        assertEquals("Alice, 30", res.get(1));
        assertEquals("Bob, 25", res.get(2));
    }

    @Test
    public void testWriteDataToFileThrowsCSVFileNotFoundException() {
        assertThrows(CSVFileNotFoundException.class, () ->
                csvGenerator.writeDataToFile(new ArrayList<>(), "invalid/path/to/file.txt"));
    }

}
