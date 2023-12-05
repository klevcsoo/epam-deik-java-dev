package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.movie.persistance.MovieRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @Autowired
    private final MovieRepository movieRepository;

    @Override
    public void set(Movie entity) {
        movieRepository.save(entity);
    }

    @Override
    public void delete(String title) {
        movieRepository.deleteById(title);
    }

    @Override
    public List<Movie> list() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> get(String title) {
        return movieRepository.findById(title);
    }
}
