package com.aluracursos.screenmatch.services;

import com.aluracursos.screenmatch.dto.EpisodeDTO;
import com.aluracursos.screenmatch.dto.SeriesDTO;
import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository repository;

    private List<SeriesDTO> convertSeries(List<Series> series) {
        return series
                .stream()
                .map(s -> new SeriesDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getRating(),
                        s.getSeasons(),
                        s.getPoster(),
                        s.getGenre(),
                        s.getActors(),
                        s.getSynopsis()
                ))
                .toList();
    }

    private List<EpisodeDTO> convertEpisodes(List<Episode> episodes) {
        return episodes
                .stream()
                .map(episode -> new EpisodeDTO(
                        episode.getTitle(),
                        episode.getSeason(),
                        episode.getNumber())
                )
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> getAllSeries() {
        return convertSeries(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return convertSeries(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getLatestSeries() {
        return convertSeries(repository.findLatestReleases());
    }

    public SeriesDTO getSeriesById(Long id) {
        Optional<Series> optionalSeries = repository.findById(id);
        if (optionalSeries.isEmpty()) return null;
        Series series = optionalSeries.get();

        return new SeriesDTO(
                series.getId(),
                series.getTitle(),
                series.getRating(),
                series.getSeasons(),
                series.getPoster(),
                series.getGenre(),
                series.getActors(),
                series.getSynopsis()
        );

    }

    public List<EpisodeDTO> getAllEpisodes(Long id) {
        Optional<Series> optionalSeries = repository.findById(id);
        if (optionalSeries.isEmpty()) return null;
        Series series = optionalSeries.get();
        return convertEpisodes(series.getEpisodes());
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Long season) {
        return convertEpisodes(repository.findSeasonByNumber(id, season));
    }

    public List<SeriesDTO> getSeriesByGenre(String genre) {
        Genre category = Genre.fromString(genre);
        return convertSeries(repository.findByGenre(category));
    }
}
