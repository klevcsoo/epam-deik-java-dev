package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.persistance.Room;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistance.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommands {
    @Autowired
    private final RoomService roomService;

    @Autowired
    private final UserService userService;

    @ShellMethod(key = "create room", value = "Create a new room")
    public String createRoom(String name, Integer rows, Integer seatsPerRow) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        try {
            roomService.set(new Room(name, rows, seatsPerRow));
        } catch (Exception e) {
            return "Failed to create room: " + e.getLocalizedMessage();
        }

        return "Room created.";
    }

    @ShellMethod(key = "update room", value = "Update an existing room")
    public String updateRoom(String title, Integer rows, Integer seatsPerRow) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        if (roomService.get(title).isEmpty()) {
            return "Room doesn't exist. You can create one via the 'create room' command.";
        }

        try {
            roomService.set(new Room(title, rows, seatsPerRow));
        } catch (Exception e) {
            return "Failed to update room: " + e.getLocalizedMessage();
        }

        return "Room updated.";
    }

    @ShellMethod(key = "delete room", value = "Delete a room")
    public String deleteRoom(String title) {
        if (!userService.isAuthenticated(User.Role.ADMIN)) {
            return "You need to be signed in as a privileged user to use this command.";
        }

        if (roomService.get(title).isEmpty()) {
            return "Room doesn't exist. You can create one via the 'create room' command.";
        }

        try {
            roomService.delete(title);
        } catch (Exception e) {
            return "Failed to delete room: " + e.getLocalizedMessage();
        }

        return "Room deleted.";
    }

    @ShellMethod(key = "list rooms", value = "List the existing rooms")
    public String listRooms() {
        try {
            List<Room> rooms = roomService.list(); if (rooms.isEmpty()) {
                return "There are no rooms at the moment.";
            } else {
                StringBuilder builder = new StringBuilder();
                rooms.forEach(room -> builder.append("Room ").append(room.getName()).append(" with ").append(room.getSeatsPerRow() * room.getRows()).append(" seats, ").append(room.getRows()).append(" rows and ").append(room.getSeatsPerRow()).append(" columns"));
                return builder.toString();
            }
        } catch (Exception e) {
            return "Failed to fetch rooms: " + e.getLocalizedMessage();
        }
    }
}
