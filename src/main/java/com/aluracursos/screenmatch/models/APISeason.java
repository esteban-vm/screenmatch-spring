package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record APISeason(
        @JsonAlias("APISeason") int number,
        @JsonAlias("Episodes") List<APIEpisode> episodes
) {
}
