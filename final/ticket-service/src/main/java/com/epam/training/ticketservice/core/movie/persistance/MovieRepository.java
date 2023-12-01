package com.epam.training.ticketservice.core.movie.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> { }
