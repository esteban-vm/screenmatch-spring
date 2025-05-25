package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(
        @JsonAlias("Title") String title,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("totalSeasons") int numberOfSeasons,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Actors") String actors,
        @JsonAlias("Plot") String synopsis
) {
}
