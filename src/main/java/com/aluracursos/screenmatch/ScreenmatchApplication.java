package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.services.API;
import com.aluracursos.screenmatch.services.DataConversor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) {
        API api = new API();
        DataConversor conversor = new DataConversor();

        var json = api.getData("http://www.omdbapi.com/?apikey=9c53b1c8&t=game-of-thrones");
        var series = conversor.getData(json, Series.class);
        System.out.println(series);

        json = api.getData("http://www.omdbapi.com/?apikey=9c53b1c8&t=game-of-thrones&Season=1&episode=1");
        var episode = conversor.getData(json, Episode.class);
        System.out.println(episode);
    }
}
