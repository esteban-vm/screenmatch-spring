package com.aluracursos.screenmatch.models;

public enum Genre {
    ACTION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDY("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crimen");

    private final String genreOMDB;
    private final String genreSpanish;

    Genre(String genreOMDB, String genreSpanish) {
        this.genreOMDB = genreOMDB;
        this.genreSpanish = genreSpanish;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOMDB.equalsIgnoreCase(text) || genre.genreSpanish.equalsIgnoreCase(text)) {
                return genre;
            }
        }

        throw new IllegalArgumentException("Ningún género encontrado: " + text);
    }
}
