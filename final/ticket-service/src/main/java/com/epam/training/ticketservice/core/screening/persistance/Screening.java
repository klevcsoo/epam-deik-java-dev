package com.epam.training.ticketservice.core.screening.persistance;

import com.epam.training.ticketservice.core.movie.persistance.Movie;
import com.epam.training.ticketservice.core.room.persistance.Room;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "screenings")
@Data
@NoArgsConstructor
public class Screening {

    @EmbeddedId
    private ScreeningId id;

    public Screening(Movie movie, Room room, LocalDateTime time) {
        this.id = new ScreeningId(movie, room, time);
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return getId().getMovie().getTitle() + " (" + getId().getMovie().getGenre() + ", "
            + getId().getMovie().getLength() + " minutes), screened in room " + getId().getRoom()
            .getName() + ", at " + getId().getTime().format(format);
    }
}
