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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String URL_BASE = "http://www.omdbapi.com/";
    private static final String API_KEY = "9c53b1c8";
    private final Scanner scanner = new Scanner(System.in);
    private final API api = new API();
    private final DataConversor conversor = new DataConversor();


    public void showMenu() {
        System.out.println("\uD83D\uDCDD Escribe el nombre de la serie que deseas buscar:");
        var seriesTitle = scanner.nextLine();
        var encodedTitle = URLEncoder.encode(seriesTitle, Charset.defaultCharset());
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

        System.out.println("\uD83D\uDCDD Indica el año a partir del cual deseas ver la serie:");
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
    }
}
