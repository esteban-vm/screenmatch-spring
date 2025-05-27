package com.aluracursos.screenmatch.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private double rating;
    private int season;
    private int episode;
    private LocalDate dateOfRelease;
    @ManyToOne
    private Series series;

    public Movie() {
    }

    public Movie(int number, DataEpisode data) {
        this.title = data.title();

        try {
            this.rating = Double.parseDouble(data.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        this.season = number;
        this.episode = data.number();

        try {
            this.dateOfRelease = LocalDate.parse(data.dateOfRelease());
        } catch (DateTimeParseException e) {
            this.dateOfRelease = null;
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", season=" + season +
                ", episode=" + episode +
                ", dateOfRelease=" + dateOfRelease +
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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
