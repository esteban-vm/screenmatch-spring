package com.aluracursos.screenmatch.main;

import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.repositories.SeriesRepository;
import com.aluracursos.screenmatch.services.APIConsumer;
import com.aluracursos.screenmatch.services.DataConversor;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = System.getenv("OMDB_API_KEY");
    private static final String URL_BASE = "http://www.omdbapi.com/";
    private static final String URL = URL_BASE + "?apikey=" + API_KEY + "&t=";

    private final SeriesRepository repository;
    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer consumer = new APIConsumer();
    private final DataConversor conversor = new DataConversor();

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var menu = """
                1 - Buscar series.
                2 - Buscar episodios por serie.
                3 - Mostrar series guardadas.
                0 - Salir.
                """;

        var option = -1;

        while (option != 0) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> searchSeries();
                case 2 -> searchEpisodes();
                case 3 -> showSavedSeries();
                case 0 -> System.out.println("Cerrando la aplicación");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void searchSeries() {
        var data = getSeriesFromAPI();
        var series = new Series(data);
        repository.save(series);
        System.out.println(data);
    }

    private void searchEpisodes() {
        var data = getSeriesFromAPI();
        var encoded = encodeTitle(data.title());
        var seasons = new ArrayList<DataSeason>();

        for (int i = 1; i <= data.numberOfSeasons(); i++) {
            var json = consumer.getDataFromAPI(URL + encoded + "&DataSeason=" + i);
            var season = conversor.getData(json, DataSeason.class);
            seasons.add(season);
        }

        seasons.forEach(System.out::println);
    }

    private void showSavedSeries() {
        var savedSeries = repository.findAll();

        savedSeries.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private DataSeries getSeriesFromAPI() {
        System.out.println("\n\uD83D\uDCDD Escribe el título de la serie que deseas buscar:");

        var title = scanner.nextLine();
        var encoded = encodeTitle(title);
        var json = consumer.getDataFromAPI(URL + encoded);

        return conversor.getData(json, DataSeries.class);
    }

    private String encodeTitle(String title) {
        return URLEncoder.encode(title.trim(), Charset.defaultCharset());
    }
}
