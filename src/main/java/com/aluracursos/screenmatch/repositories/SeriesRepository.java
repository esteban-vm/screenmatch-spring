package com.aluracursos.screenmatch.repositories;

import com.aluracursos.screenmatch.models.Episode;
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

    // @Query("SELECT e FROM Series s JOIN s.episodes e WHERE LOWER(e.title) = LOWER(:episodeTitle)")
    @Query("SELECT s FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:episodeTitle%")
    List<Episode> findEpisodesByTitle(String episodeTitle);

    @Query("SELECT s FROM Series s JOIN s.episodes e WHERE s = :series ORDER BY e.rating DESC LIMIT 5")
    List<Episode> findTop5EpisodesBySeries(Series series);

    @Query("SELECT s FROM Series s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.dateOfRelease) DESC LIMIT 5")
    List<Series> findLatestReleases();
}
