package com.aluracursos.screenmatch.repositories;

import com.aluracursos.screenmatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
