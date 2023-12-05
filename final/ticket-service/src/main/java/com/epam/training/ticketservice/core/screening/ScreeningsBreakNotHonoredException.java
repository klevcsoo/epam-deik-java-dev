package com.epam.training.ticketservice.core.screening;

public class ScreeningsBreakNotHonoredException extends RuntimeException {

    public ScreeningsBreakNotHonoredException() {
        super("This would start in the break period after another screening in this room");
    }
}
