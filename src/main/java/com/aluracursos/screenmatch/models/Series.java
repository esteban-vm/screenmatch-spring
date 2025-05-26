package com.aluracursos.screenmatch.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String title;
    private double rating;
    private int numberOfSeasons;
    private String poster;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String actors;
    private String synopsis;
    @Transient
    private List<Movie> movies;

    public Series() {
    }

    public Series(DataSeries data) {
        this.title = data.title();
        this.rating = OptionalDouble.of(Double.parseDouble(data.rating())).orElse(0.0);
        this.numberOfSeasons = data.numberOfSeasons();
        this.poster = data.poster();
        this.genre = Genre.fromString(data.genre().split(",")[0].trim());
        this.actors = data.actors();
        this.synopsis = data.synopsis();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
