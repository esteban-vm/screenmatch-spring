package com.aluracursos.screenmatch.repositories;

import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String title);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);
}
