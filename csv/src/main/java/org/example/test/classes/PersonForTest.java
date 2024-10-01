package org.example.test.classes;

import lombok.Getter;
import lombok.Setter;
import org.example.annotation.CSVColumn;

@Getter
@Setter
public class PersonForTest {
    @CSVColumn(header = "name")
    private String name;
    @CSVColumn(header = "age")
    private int age;

    public PersonForTest(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
