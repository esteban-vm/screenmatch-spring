package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Series(
        @JsonAlias("Title") String title,
        @JsonAlias("imdbRating") String rating,
        String totalSeasons) {
}
