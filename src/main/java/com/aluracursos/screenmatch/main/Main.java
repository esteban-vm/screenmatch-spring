package com.aluracursos.screenmatch.main;

import com.aluracursos.screenmatch.models.Movie;
import com.aluracursos.screenmatch.models.Season;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.services.API;
import com.aluracursos.screenmatch.services.DataConversor;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String URL_BASE = "http://www.omdbapi.com/";
    private static final String API_KEY = "9c53b1c8";
    private final Scanner scanner = new Scanner(System.in);
    private final API api = new API();
    private final DataConversor conversor = new DataConversor();


    public void showMenu() {
        var leadingMenuPart = "\n\uD83D\uDCDD ";
        System.out.println(leadingMenuPart + "Escribe el título de la serie que deseas buscar:");
        var seriesTitle = scanner.nextLine();

        var encodedTitle = URLEncoder.encode(seriesTitle.trim(), Charset.defaultCharset());
        var url = URL_BASE + "?apikey=" + API_KEY + "&t=" + encodedTitle;
        var seriesJson = api.call(url);
        var series = conversor.getData(seriesJson, Series.class);
        var seasons = new ArrayList<Season>();

        for (int i = 1; i <= series.numberOfSeasons(); i++) {
            var seasonJson = api.call(url + "&Season=" + i);
            var season = conversor.getData(seasonJson, Season.class);
            seasons.add(season);
        }

        // for (Season season : seasons) {
        //     List<Episode> episodes = season.episodes();
        //
        //     for (Episode episode : episodes) {
        //         System.out.println(episode.title());
        //     }
        // }

        // Consumer<Episode> episodeConsumer = episode -> System.out.println(episode.title());
        // seasons.forEach(season -> season.episodes().forEach(episodeConsumer));

        // List<Episode> episodes = seasons.stream()
        //         .flatMap(season -> season.episodes().stream())
        //         .toList();

        // Top 5 Episodes
        // System.out.println("Los 5 mejores episodios:");
        //
        // episodes.stream()
        //         .filter(episode -> !episode.rating().equalsIgnoreCase("n/a"))
        //         .sorted(Comparator.comparing(Episode::rating).reversed())
        //         .limit(5)
        //         .forEach(System.out::println);

        List<Movie> movies = seasons.stream()
                .flatMap(season -> season
                        .episodes()
                        .stream()
                        .map(episode -> new Movie(season.number(), episode)))
                .toList();

        movies.forEach(System.out::println);

        seriesTitle = "'" + seriesTitle.toUpperCase() + "'";

        System.out.println(leadingMenuPart
                + "Escribe el año a partir del cual deseas ver la serie "
                + seriesTitle
                + ":");

        var seriesYear = scanner.nextInt();
        scanner.nextLine();

        LocalDate dateOfSeries = LocalDate.of(seriesYear, 1, 1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        movies.stream()
                .filter(movie -> {
                    LocalDate release = movie.getDateOfRelease();
                    return release != null && release.isAfter(dateOfSeries);
                })
                .forEach(movie -> System.out.println("\uD83C\uDFAC" +
                        " Temporada: "
                        + movie.getSeason()
                        + ", Título: '" +
                        movie.getTitle()
                        + "', Fecha de lanzamiento: "
                        + movie.getDateOfRelease()
                        .format(dateTimeFormatter)
                ));

        System.out.println(leadingMenuPart
                + "Escribe el título del episodio específico de la serie "
                + seriesTitle
                + " que deseas ver:");

        var episodeTitle = scanner.nextLine();

        Optional<Movie> movieOptional = movies
                .stream()
                .filter(movie -> movie
                        .getTitle()
                        .toUpperCase()
                        .contains(episodeTitle.toUpperCase()))
                .findFirst();

        if (movieOptional.isPresent()) {
            System.out.println("\uD83D\uDC4D Episodio encontrado:");
            System.out.println(movieOptional.get());
        } else {
            System.out.println("\uD83D\uDC4E Episodio no encontrado");
        }

        Map<Integer, Double> ratingsPerSeason = movies.stream()
                .filter(movie -> movie.getRating() > 0.0)
                .collect(Collectors.groupingBy(Movie::getSeason,
                        Collectors.averagingDouble(Movie::getRating)));

        System.out.println("\nPuntuaciones por temporada: " + ratingsPerSeason);

        DoubleSummaryStatistics statistics = movies.stream()
                .filter(movie -> movie.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Movie::getRating));

        System.out.println("\nPromedio de puntuaciones: " + statistics.getAverage());
        System.out.println("Mejor puntuación: " + statistics.getMax());
        System.out.println("Peor puntuación: " + statistics.getMin());
    }
}
