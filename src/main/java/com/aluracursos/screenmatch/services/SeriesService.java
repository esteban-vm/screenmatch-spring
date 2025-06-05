package com.aluracursos.screenmatch.services;

import com.aluracursos.screenmatch.dto.SeriesDTO;
import com.aluracursos.screenmatch.models.Series;
import com.aluracursos.screenmatch.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository repository;

    private List<SeriesDTO> convertData(List<Series> series) {
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

    public List<SeriesDTO> getAllSeries() {
        return convertData(repository.findAll());
    }

    public List<SeriesDTO> getTop5Series() {
        return convertData(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> getLatestSeries() {
        return convertData(repository.findLatestReleases());
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
}
