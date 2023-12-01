package com.epam.training.ticketservice.core.user;

public class UnauthenticatedException extends UserException {

    public UnauthenticatedException() {
        super("You are not signed in.");
    }
}
