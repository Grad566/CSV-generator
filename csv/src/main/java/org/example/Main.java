package org.example;

import org.example.generator.CSVGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var obj1 = new SimpleForTest("Den", 2);
        var obj2 = new SimpleForTest("Den2", 3);
        var csv = new CSVGenerator(new HashMap<>());

        var list = new ArrayList<SimpleForTest>(List.of(obj1, obj2));

        csv.writeDataToFile(list, "out.txt");
    }
}