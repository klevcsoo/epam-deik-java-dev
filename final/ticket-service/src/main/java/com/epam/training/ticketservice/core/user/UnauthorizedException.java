package com.epam.training.ticketservice.core.user;

public class UnauthorizedException extends UserException {

    public UnauthorizedException() {
        super("You are not authorized to do this.");
    }
}
