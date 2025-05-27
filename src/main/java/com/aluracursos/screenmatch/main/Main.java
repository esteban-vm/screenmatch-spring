package com.aluracursos.screenmatch.main;

import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.models.Movie;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.repositories.SeriesRepository;
import com.aluracursos.screenmatch.services.APIConsumer;
import com.aluracursos.screenmatch.services.DataConversor;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

public class Main {
    private static final String URL_BASE = "http://www.omdbapi.com/";
    private static final String API_KEY = System.getenv("OMDB_API_KEY");
    private static final String URL = URL_BASE + "?apikey=" + API_KEY + "&t=";

    private final SeriesRepository repository;
    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer consumer = new APIConsumer();
    private final DataConversor conversor = new DataConversor();

    private List<Series> savedSeries = new ArrayList<>();

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var menu = """
                1 - Buscar series.
                2 - Buscar episodios.
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
        showSavedSeries();

        System.out.println("Escribe el título de la serie de la que quieres ver los episodios:");
        var title = scanner.nextLine();

        Optional<Series> optionalSeries = savedSeries
                .stream()
                .filter(series -> series
                        .getTitle()
                        .toLowerCase()
                        .contains(title.toLowerCase())
                )
                .findFirst();

        if (optionalSeries.isPresent()) {
            var seasons = new ArrayList<DataSeason>();
            var series = optionalSeries.get();
            var encoded = encodeTitle(series.getTitle());
            var numberOfSeasons = series.getNumberOfSeasons();

            for (int i = 1; i <= numberOfSeasons; i++) {
                var json = consumer.getDataFromAPI(URL + encoded + "&Season=" + i);
                var season = conversor.getData(json, DataSeason.class);
                seasons.add(season);
            }

            seasons.forEach(System.out::println);

            List<Movie> movies = seasons
                    .stream()
                    .flatMap(season -> season.episodes()
                            .stream()
                            .map(episode -> new Movie(season.number(), episode)))
                    .toList();

            series.setMovies(movies);
            repository.save(series);
        }
    }

    private void showSavedSeries() {
        savedSeries = repository
                .findAll();

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
