package com.epam.training.ticketservice.core.room;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException() {
        super("Room not found.");
    }
}
