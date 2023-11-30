package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.persistance.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void setMovie(String title, String genre, Integer length);

    void deleteMovie(String title);

    List<Movie> listMovies();

    Optional<Movie> getMovie(String title);
}
