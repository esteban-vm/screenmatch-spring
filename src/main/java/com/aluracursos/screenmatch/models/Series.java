package com.aluracursos.screenmatch.models;

import java.util.OptionalDouble;

public class Series {
    private String title;
    private double rating;
    private int numberOfSeasons;
    private String poster;
    private Genre genre;
    private String actors;
    private String synopsis;

    public Series(APISeries series) {
        title = series.title();
        rating = OptionalDouble.of(Double.parseDouble(series.rating())).orElse(0.0);
        numberOfSeasons = series.numberOfSeasons();
        poster = series.poster();
        genre = Genre.fromString(series.genre().split(",")[0].trim());
        actors = series.actors();
        synopsis = series.synopsis();
    }

    @Override
    public String toString() {
        return "Series{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", numberOfSeasons=" + numberOfSeasons +
                ", poster='" + poster + '\'' +
                ", genre=" + genre +
                ", actors='" + actors + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
