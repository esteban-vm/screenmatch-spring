package com.aluracursos.screenmatch.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeriesController {
    @GetMapping("/series")
    public String showMessage() {
        return "Hola mundo web";
    }
}
