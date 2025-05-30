package com.aluracursos.screenmatch.main;

import com.aluracursos.screenmatch.models.*;
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
    private Optional<Series> searchedSeries = Optional.empty();

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var menu = """
                
                1 - Buscar serie desde API.
                2 - Buscar serie guardada por título.
                3 - Buscar series guardadas por género.
                4 - Buscar episodios por serie guardada.
                5 - Buscar episodio guardado por título.
                6 - Mostrar la lista de series guardadas.
                7 - Mostrar las 5 mejores series guardadas.
                8 - Filtrar series guardadas por temporada y puntuación.
                9 - Mostrar los 5 mejores episodios por serie guardada.
                0 - Salir.
                """;

        var option = -1;

        while (option != 0) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> searchSeriesFromAPI();
                case 2 -> searchSeriesByTitle();
                case 3 -> searchSeriesByGenre();
                case 4 -> searchEpisodesBySeries();
                case 5 -> searchEpisodeByTitle();
                case 6 -> showSeriesList();
                case 7 -> showTop5Series();
                case 8 -> filterSeriesBySeasonAndRating();
                case 9 -> showTop5EpisodesBySeries();
                case 0 -> System.out.println("Cerrando la aplicación");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void searchSeriesFromAPI() {
        System.out.println("Escribe el título de la serie que deseas buscar desde la API:");
        var inputTitle = scanner.nextLine();
        var encodedTitle = encodeTitle(inputTitle);
        var seriesJson = consumer.getDataFromAPI(URL + encodedTitle);
        var seriesData = conversor.getData(seriesJson, DataSeries.class);
        var series = new Series(seriesData);
        repository.save(series);
        System.out.println(seriesData);
    }

    private void searchSeriesByTitle() {
        System.out.println("Escribe el título de la serie guardada que deseas buscar:");
        var inputTitle = scanner.nextLine();
        searchedSeries = repository.findByTitleContainingIgnoreCase(inputTitle);

        if (searchedSeries.isPresent()) {
            System.out.println("Serie encontrada: " + searchedSeries.get());
        } else {
            System.out.println("Serie no encontrada");
        }
    }

    private void searchSeriesByGenre() {
        System.out.println("Escribe el género del cual deseas buscar las series:");
        var inputGenre = scanner.nextLine();
        var genre = Genre.fromString(inputGenre);
        var seriesList = repository.findByGenre(genre);
        seriesList.forEach(series -> {
            String info = "Serie: "
                    + series.getTitle()
                    + " - Género: "
                    + series.getGenre();

            System.out.println(info);
        });
    }

    private void searchEpisodesBySeries() {
        showSeriesList();
        System.out.println("Escribe el título de la serie guardada de la que quieres ver los episodios:");
        var inputTitle = scanner.nextLine();

        searchedSeries = savedSeries.stream()
                .filter(series -> series
                        .getTitle()
                        .toLowerCase()
                        .contains(inputTitle.toLowerCase()))
                .findFirst();

        if (searchedSeries.isPresent()) {
            var seasons = new ArrayList<DataSeason>();
            var series = searchedSeries.get();
            var encodedTitle = encodeTitle(series.getTitle());
            var numberOfSeasons = series.getSeasons();

            for (int i = 1; i <= numberOfSeasons; i++) {
                var seasonJson = consumer.getDataFromAPI(URL + encodedTitle + "&Season=" + i);
                var seasonData = conversor.getData(seasonJson, DataSeason.class);
                seasons.add(seasonData);
            }

            seasons.forEach(System.out::println);

            var episodes = seasons
                    .stream()
                    .flatMap(season -> season.episodes()
                            .stream()
                            .map(episode -> new Episode(season.number(), episode)))
                    .toList();

            series.setEpisodes(episodes);
            repository.save(series);
        } else {
            System.out.println("Serie no encontrada");
        }
    }

    private void searchEpisodeByTitle() {
        System.out.println("Escribe el título del episodio guardado que deseas buscar:");
        var inputTitle = scanner.nextLine();
        var episodeList = repository.findEpisodesByTitle(inputTitle);
        episodeList.forEach(episode -> {
            String info = "Episodio: "
                    + episode.getTitle()
                    + " - Serie: "
                    + episode.getSeries().getTitle()
                    + " - Número de temporada: "
                    + episode.getSeason()
                    + " - Número de episodio: "
                    + episode.getNumber()
                    + " - Puntuación: "
                    + episode.getRating();

            System.out.println(info);
        });
    }

    private void showSeriesList() {
        savedSeries = repository.findAll();

        savedSeries.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private void showTop5Series() {
        var seriesList = repository.findTop5ByOrderByRatingDesc();
        seriesList.forEach(series -> {
            String info = "Serie: "
                    + series.getTitle()
                    + " - Puntuación: "
                    + series.getRating();

            System.out.println(info);
        });
    }

    private void filterSeriesBySeasonAndRating() {
        System.out.println("¿Filtrar series con cuántas temporadas como máximo?");
        var inputSeasons = scanner.nextInt();
        scanner.nextLine();
        System.out.println("¿A partir de qué puntuación?");
        var inputRating = scanner.nextDouble();
        scanner.nextLine();
        // var seriesList = repository.findBySeasonsLessThanEqualAndRatingGreaterThanEqual(inputSeasons, inputRating);
        var seriesList = repository.findBySeasonsAndRating(inputSeasons, inputRating);
        seriesList.forEach(series -> {
            String info = "Serie: "
                    + series.getTitle()
                    + " - Total de temporadas: "
                    + series.getSeasons()
                    + " - Puntuación: "
                    + series.getRating();

            System.out.println(info);
        });
    }

    private void showTop5EpisodesBySeries() {
        searchSeriesByTitle();

        if (searchedSeries.isPresent()) {
            var series = searchedSeries.get();
            var episodeList = repository.findTop5EpisodesBySeries(series);
            episodeList.forEach(episode -> {
                String info = "Episodio: "
                        + episode.getTitle()
                        + " - Número de temporada: "
                        + episode.getSeason()
                        + " - Número de episodio: "
                        + episode.getNumber()
                        + " - Puntuación: "
                        + episode.getRating();

                System.out.println(info);
            });
        }
    }

    private String encodeTitle(String title) {
        return URLEncoder.encode(title.trim(), Charset.defaultCharset());
    }
}
