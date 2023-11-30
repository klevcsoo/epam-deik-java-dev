package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.movie.persistance.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    @Autowired
    private final MovieRepository movieRepository;

    @Override
    public void setMovie(String title, String genre, Integer length) {
        Movie movie = movieRepository.findByTitle(title).orElse(new Movie(title, genre, length));
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String title) {
        movieRepository.deleteById(title);
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll(Sort.by("title").ascending());
    }

    @Override
    public Optional<Movie> getMovie(String title) {
        return movieRepository.findById(title);
    }
}
