package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Series(
        @JsonAlias("Title") String title,
        @JsonAlias("imdbRating") String rating,
        String totalSeasons) {
}
