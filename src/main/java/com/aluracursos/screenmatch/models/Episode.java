package com.aluracursos.screenmatch.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private Double rating;
    private Integer season;
    private Integer number;
    private LocalDate dateOfRelease;
    @ManyToOne
    private Series series;

    public Episode() {
    }

    public Episode(Integer number, DataEpisode data) {
        this.title = data.title();

        try {
            this.rating = Double.parseDouble(data.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        this.season = number;
        this.number = data.number();

        try {
            this.dateOfRelease = LocalDate.parse(data.dateOfRelease());
        } catch (DateTimeParseException e) {
            this.dateOfRelease = null;
        }
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", season=" + season +
                ", episode=" + number +
                ", dateOfRelease=" + dateOfRelease +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
