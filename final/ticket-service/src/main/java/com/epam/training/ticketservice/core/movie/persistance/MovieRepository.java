package com.epam.training.ticketservice.core.movie.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, String> {
    Optional<Movie> findByTitle(String title);
}
