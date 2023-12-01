package com.epam.training.ticketservice.core.movie;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException() {
        super("Movie not found.");
    }
}
