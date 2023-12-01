package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomNotFoundException;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.persistance.Room;
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
public class RoomCommands {

    @Autowired
    private final RoomService roomService;

    @Autowired
    private final UserService userService;

    @ShellMethod(key = "create room", value = "Create a new room")
    public String createRoom(String name, Integer rows, Integer seatsPerRow) {
        try {
            userService.requireAuthorization(Role.ADMIN);
            roomService.set(new Room(name, rows, seatsPerRow));
        } catch (UserException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to create room: " + e.getLocalizedMessage();
        }

        return "Room created.";
    }

    @ShellMethod(key = "update room", value = "Update an existing room")
    public String updateRoom(String title, Integer rows, Integer seatsPerRow) {
        try {
            userService.requireAuthorization(Role.ADMIN);

            if (roomService.get(title).isEmpty()) {
                throw new RoomNotFoundException();
            }

            roomService.set(new Room(title, rows, seatsPerRow));
        } catch (UserException | RoomNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to update room: " + e.getLocalizedMessage();
        }

        return "Room updated.";
    }

    @ShellMethod(key = "delete room", value = "Delete a room")
    public String deleteRoom(String title) {
        try {
            userService.requireAuthorization(Role.ADMIN);

            if (roomService.get(title).isEmpty()) {
                throw new RoomNotFoundException();
            }

            roomService.delete(title);
        } catch (UserException | RoomNotFoundException e) {
            return e.getLocalizedMessage();
        } catch (Exception e) {
            return "Failed to delete room: " + e.getLocalizedMessage();
        }

        return "Room deleted.";
    }

    @ShellMethod(key = "list rooms", value = "List the existing rooms")
    public String listRooms() {
        try {
            List<Room> rooms = roomService.list();
            if (rooms.isEmpty()) {
                return "There are no rooms at the moment.";
            } else {
                return rooms.stream().map(Room::toString).collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            return "Failed to fetch rooms: " + e.getLocalizedMessage();
        }
    }
}
