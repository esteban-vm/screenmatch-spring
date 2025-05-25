package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record APIEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("APIEpisode") int number,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String dateOfRelease
) {
}
