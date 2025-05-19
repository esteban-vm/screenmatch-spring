package com.aluracursos.screenmatch.main;

import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public void showExample() {
        List<String> names = Arrays.asList("Brenda", "Luis", "María Fernanda", "Eric", "Genesys");

        names.stream()
                .sorted()
                .limit(4)
                .filter(name -> name.startsWith("L"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
