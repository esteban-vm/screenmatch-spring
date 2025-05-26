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
    private static final String API_KEY = "9c53b1c8";
    private static final String URL_BASE = "http://www.omdbapi.com/";
    private static final String URL = URL_BASE + "?apikey=" + API_KEY + "&t=";

    private final SeriesRepository repository;
    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer consumer = new APIConsumer();
    private final DataConversor conversor = new DataConversor();

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

//    public void showMenu() {
//        var leadingMenuPart = "\n\uD83D\uDCDD ";
//        System.out.println(leadingMenuPart + "Escribe el título de la serie que deseas buscar:");
//        var seriesTitle = scanner.nextLine();
//
//        var encodedTitle = URLEncoder.encode(seriesTitle.trim(), Charset.defaultCharset());
//        var url = URL_BASE + "?apikey=" + API_KEY + "&t=" + encodedTitle;
//        var seriesJson = consumer.getDataFromAPI(url);
//        var series = conversor.getData(seriesJson, DataSeries.class);
//        var seasons = new ArrayList<DataSeason>();
//
//        for (int i = 1; i <= series.numberOfSeasons(); i++) {
//            var seasonJson = consumer.getDataFromAPI(url + "&DataSeason=" + i);
//            var season = conversor.getData(seasonJson, DataSeason.class);
//            seasons.add(season);
//        }
//
//        // for (DataSeason season : seasons) {
//        //     List<DataEpisode> episodes = season.episodes();
//        //
//        //     for (DataEpisode episode : episodes) {
//        //         System.out.println(episode.title());
//        //     }
//        // }
//
//        // Consumer<DataEpisode> episodeConsumer = episode -> System.out.println(episode.title());
//        // seasons.forEach(season -> season.episodes().forEach(episodeConsumer));
//
//        // List<DataEpisode> episodes = seasons.stream()
//        //         .flatMap(season -> season.episodes().stream())
//        //         .toList();
//
//        // Top 5 Episodes
//        // System.out.println("Los 5 mejores episodios:");
//        //
//        // episodes.stream()
//        //         .filter(episode -> !episode.rating().equalsIgnoreCase("n/a"))
//        //         .sorted(Comparator.comparing(DataEpisode::rating).reversed())
//        //         .limit(5)
//        //         .forEach(System.out::println);

//        List<Movie> movies = seasons.stream()
//                .flatMap(season -> season
//                        .episodes()
//                        .stream()
//                        .map(episode -> new Movie(season.number(), episode)))
//                .toList();
//
//        movies.forEach(System.out::println);
//
//        seriesTitle = "'" + seriesTitle.toUpperCase() + "'";
//
//        System.out.println(leadingMenuPart
//                + "Escribe el año a partir del cual deseas ver la serie "
//                + seriesTitle
//                + ":");
//
//        var seriesYear = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate dateOfSeries = LocalDate.of(seriesYear, 1, 1);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        movies.stream()
//                .filter(movie -> {
//                    LocalDate release = movie.getDateOfRelease();
//                    return release != null && release.isAfter(dateOfSeries);
//                })
//                .forEach(movie -> System.out.println("\uD83C\uDFAC" +
//                        " Temporada: "
//                        + movie.getSeason()
//                        + ", Título: '" +
//                        movie.getTitle()
//                        + "', Fecha de lanzamiento: "
//                        + movie.getDateOfRelease()
//                        .format(dateTimeFormatter)
//                ));
//
//        System.out.println(leadingMenuPart
//                + "Escribe el título del episodio específico de la serie "
//                + seriesTitle
//                + " que deseas ver:");
//
//        var episodeTitle = scanner.nextLine();
//
//        Optional<Movie> movieOptional = movies
//                .stream()
//                .filter(movie -> movie
//                        .getTitle()
//                        .toUpperCase()
//                        .contains(episodeTitle.toUpperCase()))
//                .findFirst();
//
//        if (movieOptional.isPresent()) {
//            System.out.println("\uD83D\uDC4D Episodio encontrado:");
//            System.out.println(movieOptional.get());
//        } else {
//            System.out.println("\uD83D\uDC4E Episodio no encontrado");
//        }
//
//        Map<Integer, Double> ratingsPerSeason = movies.stream()
//                .filter(movie -> movie.getRating() > 0.0)
//                .collect(Collectors.groupingBy(Movie::getSeason,
//                        Collectors.averagingDouble(Movie::getRating)));
//
//        System.out.println("\nPuntuaciones por temporada: " + ratingsPerSeason);
//
//        DoubleSummaryStatistics statistics = movies.stream()
//                .filter(movie -> movie.getRating() > 0.0)
//                .collect(Collectors.summarizingDouble(Movie::getRating));
//
//        System.out.println("\nPromedio de puntuaciones: " + statistics.getAverage());
//        System.out.println("Mejor puntuación: " + statistics.getMax());
//        System.out.println("Peor puntuación: " + statistics.getMin());
//    }

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
