package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") int number,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String dateOfRelease
) {
}
