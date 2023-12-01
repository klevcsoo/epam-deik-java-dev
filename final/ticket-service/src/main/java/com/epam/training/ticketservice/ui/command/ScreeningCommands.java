package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieNotFoundException;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.room.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.persistance.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.ScreeningsBreakNotHonoredException;
import com.epam.training.ticketservice.core.screening.ScreeningsOverlapException;
import com.epam.training.ticketservice.core.screening.persistance.Screening;
import com.epam.training.ticketservice.core.screening.persistance.ScreeningId;
import com.epam.training.ticketservice.core.user.UserException;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistance.User.Role;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommands {

    private final static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Autowired
    private final ScreeningService screeningService;

    @Autowired
    private final MovieService movieService;

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final UserService userService;

    @ShellMethod(key = "create screening", value = "Create a new screening")
    public String createScreening(String movieTitle, String roomName, String timeText) {
        try {
            LocalDateTime time = LocalDateTime.parse(timeText, format);
            userService.requireAuthorization(Role.ADMIN);

            Movie movie = movieService.get(movieTitle).orElseThrow(MovieNotFoundException::new);
            Room room = roomService.get(roomName).orElseThrow(RoomNotFoundException::new);
            screeningService.set(new Screening(movie, room, time));
        } catch (UserException | ScreeningsOverlapException | ScreeningsBreakNotHonoredException |
                 MovieNotFoundException | RoomNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to create screening: " + e.getLocalizedMessage();
        }

        return "Screening created.";
    }

    @ShellMethod(key = "delete screen", value = "Delete a screening")
    public String deleteScreening(String movieTitle, String roomName, String timeText) {
        try {
            LocalDateTime time = LocalDateTime.parse(timeText, format);
            userService.requireAuthorization(Role.ADMIN);

            Movie movie = movieService.get(movieTitle).orElseThrow(MovieNotFoundException::new);
            Room room = roomService.get(roomName).orElseThrow(RoomNotFoundException::new);
            screeningService.delete(new ScreeningId(movie, room, time));
        } catch (UserException | ScreeningsOverlapException | ScreeningsBreakNotHonoredException |
                 MovieNotFoundException | RoomNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to delete screening: " + e.getLocalizedMessage();
        }

        return "Screening deleted";
    }

    @ShellMethod(key = "list screenings", value = "List screenings")
    public String listScreenings() {
        try {
            List<Screening> screenings = screeningService.list();
            if (screenings.isEmpty()) {
                return "There are no screenings.";
            } else {
                return screenings.stream().map(Screening::toString)
                    .collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "Failed to fetch screenings: " + e.getLocalizedMessage();
        }
    }
}
