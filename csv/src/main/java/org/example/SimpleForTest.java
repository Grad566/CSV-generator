package org.example;

import org.example.annotation.CSVColumn;

public class SimpleForTest {
    @CSVColumn(header = "name")
    String name;
    @CSVColumn(header = "age")
    int age;

    public SimpleForTest(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
