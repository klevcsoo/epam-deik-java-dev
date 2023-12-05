package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.user.UserException;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistance.User.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommands {

    @Autowired
    private final MovieService movieService;

    @Autowired
    private final UserService userService;

    @ShellMethod(key = "create movie", value = "Create a new movie")
    public String createMovie(String title, String genre, Integer length) {
        try {
            userService.requireAuthorization(Role.ADMIN);
            movieService.set(new Movie(title, genre, length));
        } catch (UserException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to create movie: " + e.getLocalizedMessage();
        }

        return "Movie created.";
    }

    @ShellMethod(key = "update movie", value = "Update an existing room")
    public String updateMovie(String title, String genre, Integer length) {
        try {
            userService.requireAuthorization(Role.ADMIN);

            if (movieService.get(title).isEmpty()) {
                throw new MovieNotFoundException();
            }

            movieService.set(new Movie(title, genre, length));
        } catch (UserException | MovieNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to update movie: " + e.getLocalizedMessage();
        }

        return "Movie updated.";
    }

    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String deleteMovie(String title) {
        try {
            userService.requireAuthorization(Role.ADMIN);

            if (movieService.get(title).isEmpty()) {
                throw new MovieNotFoundException();
            }

            movieService.delete(title);
        } catch (UserException | MovieNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to delete movie: " + e.getLocalizedMessage();
        }

        return "Movie deleted.";
    }

    @ShellMethod(key = "list movies", value = "List the existing movies")
    public String listMovies() {
        try {
            List<Movie> movies = movieService.list();
            if (movies.isEmpty()) {
                return "There are no movies at the moment";
            } else {
                return movies.stream().map(Movie::toString).collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "Failed to fetch movies: " + e.getLocalizedMessage();
        }
    }
}
