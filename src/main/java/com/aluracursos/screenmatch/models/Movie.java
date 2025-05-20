package com.aluracursos.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Movie {
    private String title;
    private double rating;
    private int season;
    private int episode;
    private LocalDate dateOfRelease;

    public Movie(int number, Episode episode) {
        this.title = episode.title();

        try {
            this.rating = Double.parseDouble(episode.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        this.season = number;
        this.episode = episode.number();

        try {
            this.dateOfRelease = LocalDate.parse(episode.dateOfRelease());
        } catch (DateTimeParseException e) {
            this.dateOfRelease = null;
        }
    }

    @Override
    public String toString() {
        return "Title: '" + title + "'" +
                ", Episode: " + episode +
                ", Season: " + season +
                ", Rating: " + rating +
                ", Date of Release: " + dateOfRelease;
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

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }
}
