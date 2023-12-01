package com.epam.training.ticketservice.core.screening;

public class ScreeningsOverlapException extends RuntimeException {
    public ScreeningsOverlapException() {
        super("There is an overlapping screening.");
    }
}
