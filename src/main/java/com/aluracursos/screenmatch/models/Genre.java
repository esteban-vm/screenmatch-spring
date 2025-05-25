package com.aluracursos.screenmatch.models;

public enum Genre {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private final String genreOMDB;

    Genre(String genreOMDB) {
        this.genreOMDB = genreOMDB;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOMDB.equalsIgnoreCase(text)) {
                return genre;
            }
        }

        throw new IllegalArgumentException("Ningún género encontrado: " + text);
    }
}
