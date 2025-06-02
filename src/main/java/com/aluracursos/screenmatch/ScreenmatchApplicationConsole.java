//package com.aluracursos.screenmatch;
//
//import com.aluracursos.screenmatch.main.Main;
//import com.aluracursos.screenmatch.repositories.SeriesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationConsole implements CommandLineRunner {
//    @Autowired
//    private SeriesRepository repository;
//
//    public static void main(String[] args) {
//        SpringApplication.run(ScreenmatchApplicationConsole.class, args);
//    }
//
//    @Override
//    public void run(String... args) {
//        Main main = new Main(repository);
//        main.showMenu();
//    }
//}
