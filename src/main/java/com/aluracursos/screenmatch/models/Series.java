package com.aluracursos.screenmatch.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String title;
    private Double rating;
    private Integer seasons;
    private String poster;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String actors;
    private String synopsis;
    @OneToMany(mappedBy = "series")
    private List<Episode> episodes;

    public Series() {
    }

    public Series(DataSeries data) {
        this.title = data.title();
        this.rating = OptionalDouble.of(Double.parseDouble(data.rating())).orElse(0.0);
        this.seasons = data.numberOfSeasons();
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
                ", numberOfSeasons=" + seasons +
                ", poster='" + poster + '\'' +
                ", genre=" + genre +
                ", actors='" + actors + '\'' +
                ", synopsis='" + synopsis + '\'' +
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

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
