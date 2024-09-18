package org.example.test.classes;

import org.example.annotation.CSVColumn;

public class PersonForTest {
    @CSVColumn(header = "name")
    String name;
    @CSVColumn(header = "age")
    int age;

    public PersonForTest(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
