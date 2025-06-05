package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.models.Genre;

public record SeriesDTO(Long id,
                        String title,
                        Double rating,
                        Integer seasons,
                        String poster,
                        Genre genre,
                        String actors,
                        String synopsis) {
}
