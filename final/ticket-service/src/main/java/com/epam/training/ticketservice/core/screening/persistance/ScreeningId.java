package com.epam.training.ticketservice.core.screening.persistance;

import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.room.persistance.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningId implements Serializable {
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Room room;

    private LocalDateTime time;
}
