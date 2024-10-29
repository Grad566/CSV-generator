package org.example.service;

import org.example.exception.DataWriterException;
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

import static org.junit.jupiter.api.Assertions.*;

class DataWriterServiceTest {
    private DataWriterService service;
    private String testFilePath;
    private String testFilePathWithSettings;

    @BeforeEach
    public void setUp() {
        testFilePath = "src/test/test_out.txt";
        testFilePathWithSettings = "src/test/test_out2.txt";
        service = new DataWriterService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(testFilePath));
        Files.deleteIfExists(Path.of(testFilePathWithSettings));
    }

    @Test
    public void testWriteDataToFile() throws IOException, DataWriterException {
        PersonForTest person1 = new PersonForTest("John", 29);
        PersonForTest person2 = new PersonForTest("Alex", 5);
        List<PersonForTest> list = new ArrayList<>(List.of(person1, person2));

        service.writeData(list, testFilePath, new HashMap<>());

        List<String> res = Files.readAllLines(Path.of(testFilePath));
        assertEquals("name, age", res.get(0));
        assertEquals("John, 29", res.get(1));
        assertEquals("Alex, 5", res.get(2));
    }

    @Test
    public void testWriteArrayToFile() throws DataWriterException, IOException {
        PersonForTest[] people = {
                new PersonForTest("Alice", 30),
                new PersonForTest("Bob", 25)
        };

        service.writeData(people, testFilePath, new HashMap<>());

        List<String> res = Files.readAllLines(Path.of(testFilePath));
        assertEquals("name, age", res.get(0));
        assertEquals("Alice, 30", res.get(1));
        assertEquals("Bob, 25", res.get(2));
    }

    @Test
    public void testWriteDataToFileThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.writeData(new ArrayList<>(), testFilePath, new HashMap<>()));
    }
}