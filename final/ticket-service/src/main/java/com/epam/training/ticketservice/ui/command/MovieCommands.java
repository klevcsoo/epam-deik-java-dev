package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistance.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommands {
    @Autowired
    private final MovieService movieService;

    @Autowired
    private final UserService userService;

    @ShellMethod(key = "create movie", value = "Create a new movie")
    public String createMovie(String title, String genre, Integer length) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        try {
            movieService.setMovie(title, genre, length);
        } catch (Exception e) {
            return "Failed to create movie: " + e.getLocalizedMessage();
        }

        return "Movie created.";
    }

    @ShellMethod(key = "update movie", value = "Update an existing")
    public String updateMovie(String title, String genre, Integer length) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        if (movieService.getMovie(title).isEmpty()) {
            return "Movie doesn't exist. You can create one via the 'create movie' command.";
        }

        try {
            movieService.setMovie(title, genre, length);
        } catch (Exception e) {
            return "Failed to update movie: " + e.getLocalizedMessage();
        }

        return "Movie updated.";
    }

    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String deleteMovie(String title) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        if (movieService.getMovie(title).isEmpty()) {
            return "Movie doesn't exist. You can create one via the 'create movie' command.";
        }

        try {
            movieService.deleteMovie(title);
        } catch (Exception e) {
            return "Failed to delete movie: " + e.getLocalizedMessage();
        }

        return "Movie deleted.";
    }

    @ShellMethod(key = "list movies", value = "List the existing movies")
    public String listMovies() {
        try {
            List<Movie> movies = movieService.listMovies();
            if (movies.isEmpty()) {
                return "There are no movies at the moment.";
            } else {
                StringBuilder builder = new StringBuilder();
                movies.forEach(movie -> builder.append(movie.getTitle()).append(" (").append(movie.getGenre()).append(", ").append(movie.getLength()).append(" minutes)"));
                return builder.toString();
            }
        } catch (Exception e) {
            return "Failed to fetch movies: " + e.getLocalizedMessage();
        }
    }
}
