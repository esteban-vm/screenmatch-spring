package com.aluracursos.screenmatch.repositories;

import com.aluracursos.screenmatch.models.Genre;
import com.aluracursos.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String title);

    List<Series> findTop5ByOrderByRatingDesc();

    List<Series> findByGenre(Genre genre);

    // List<Series> findBySeasonsLessThanEqualAndRatingGreaterThanEqual(Integer seasons, Double rating);

    @Query("SELECT s FROM Series s WHERE s.seasons <= :seasons AND s.rating >= :rating")
    List<Series> findBySeasonsAndRating(Integer seasons, Double rating);
}
