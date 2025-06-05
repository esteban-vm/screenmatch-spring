package com.aluracursos.screenmatch.controllers;

import com.aluracursos.screenmatch.dto.EpisodeDTO;
import com.aluracursos.screenmatch.dto.SeriesDTO;
import com.aluracursos.screenmatch.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService service;

    @GetMapping()
    public List<SeriesDTO> getAllSeries() {
        return service.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SeriesDTO> getTop5Series() {
        return service.getTop5Series();
    }

    @GetMapping("/lanzamientos")
    public List<SeriesDTO> getLatestSeries() {
        return service.getLatestSeries();
    }

    @GetMapping("/{id}")
    public SeriesDTO getSeriesById(@PathVariable Long id) {
        return service.getSeriesById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllEpisodes(@PathVariable Long id) {
        return service.getAllEpisodes(id);
    }

    @GetMapping("/{id}/temporadas/{season}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Long season) {
        return service.getSeasonByNumber(id, season);
    }

    @GetMapping("/categoria/{genre}")
    public List<SeriesDTO> getSeriesByGenre(@PathVariable String genre) {
        return service.getSeriesByGenre(genre);
    }
}
